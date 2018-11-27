package payphone.easypay.service

import org.camunda.bpm.engine.RuntimeService
import java.math.BigDecimal
import javax.annotation.Resource
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.transaction.UserTransaction

@WebServlet(urlPatterns = ["/va"])
open class VAServlet : HttpServlet() {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Resource
    lateinit var userTransaction: UserTransaction

    @Inject
    lateinit var runtimeService: RuntimeService

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        servletContext.getRequestDispatcher("/WEB-INF/jsp/va.jsp").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse){
        val virtualAccount = req.getParameter("accountNumber")
        val amount = req.getParameter("amount").toBigDecimal()

        // Search for correlated PaymentRequest.

        val builder = entityManager.criteriaBuilder
        val criteria = builder.createQuery(VAPaymentRequest::class.java)

        val requestEntity = criteria.from(VAPaymentRequest::class.java)
        val accountAttr = requestEntity[VAPaymentRequest_.accountNumber]

        criteria.select(requestEntity)
        criteria.where(builder.equal(accountAttr, virtualAccount))

        val request = entityManager.createQuery(criteria).singleResult

        // Add new history entry.

        val entry = VAPaymentHistory(paymentRequest = request, amount = amount)

        userTransaction.begin()
        entityManager.persist(entry)
        entityManager.flush()
        userTransaction.commit()

        // Get sum of paid amount.

        val sumCriteria = builder.createQuery(BigDecimal::class.java)

        val historyEntity = sumCriteria.from(VAPaymentHistory::class.java)
        val amountAttr = historyEntity[VAPaymentHistory_.amount]
        val requestAttr = historyEntity[VAPaymentHistory_.paymentRequest]

        sumCriteria.select(builder.sum(amountAttr))
        sumCriteria.groupBy(requestAttr)
        sumCriteria.having(builder.equal(requestAttr, request))

        val amountSum = entityManager.createQuery(sumCriteria).singleResult

        // Check if amount is sufficient.

        if (amountSum >= request.targetAmount) {
            runtimeService.createMessageCorrelation("va-paid")
                    .setVariable("amount", amountSum)
                    .processInstanceBusinessKey(request.paymentId)
                    .correlate()

            // Disable account number.

            userTransaction.begin()
            request.accountNumber = null
            entityManager.flush()
            userTransaction.commit()
        }
    }
}
