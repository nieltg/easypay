package payphone.easypay.service.bank

import org.camunda.bpm.engine.RuntimeService
import payphone.easypay.service.bank.entity.BankPaymentRequest
import payphone.easypay.service.bank.entity.BankPaymentRequest_
import javax.annotation.Resource
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.transaction.UserTransaction

@WebServlet(urlPatterns = ["/bank"])
class BankConsoleServlet : HttpServlet() {
    @PersistenceContext
    lateinit var entityManager: EntityManager
    @Resource
    lateinit var userTransaction: UserTransaction
    @Inject
    lateinit var runtimeService: RuntimeService

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        servletContext.getRequestDispatcher("/WEB-INF/jsp/bank.jsp").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val amount = req.getParameter("amount").toBigDecimal()

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
