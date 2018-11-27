package payphone.easypay.service.fake

import org.camunda.bpm.engine.RuntimeService
import java.math.BigDecimal
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.transaction.UserTransaction

@WebServlet(urlPatterns = ["/bank"])
class BankServlet: HttpServlet() {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Inject
    lateinit var userTransaction:UserTransaction

    @Inject
    lateinit var runtimeService: RuntimeService

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        servletContext.getRequestDispatcher("/WEB-INF/jsp/bank.jsp").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val amount = BigDecimal(req.getParameter("amount"))

        val builder = entityManager.criteriaBuilder
        val criteria = builder.createQuery(BankPaymentRequest::class.java)

        val bankRequestEntity = criteria.from(BankPaymentRequest::class.java)
        val amountAttr = bankRequestEntity[BankPaymentRequest_.amount]

        criteria.select(bankRequestEntity)
        criteria.where(builder.equal(amountAttr, amount))

        val result = entityManager.createQuery(criteria).singleResult

        userTransaction.begin()
        result.amount = null
        entityManager.flush()
        userTransaction.commit()

        runtimeService.createMessageCorrelation("bank-paid")
                .processInstanceBusinessKey(result.paymentId)
                .correlate()
    }
}
