package payphone.easypay.service.va

import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.service.va.entity.VAPaymentRequest
import java.math.BigDecimal
import javax.annotation.ManagedBean
import javax.inject.Named
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@ManagedBean
@Named("vaToolkit")
open class VAToolkit {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    private fun generateAccountNumber(): String {
        val builder = StringBuilder()

        repeat(18) { builder.append((0..9).random()) }
        return builder.toString()
    }

    fun beginPayment(execution: DelegateExecution) {
        val paymentId = execution.businessKey
        val amount = execution.getVariable("amount") as BigDecimal

        val request = VAPaymentRequest(
                paymentId = paymentId, accountNumber = generateAccountNumber(), amount = amount)

        entityManager.persist(request)
        entityManager.flush()

        execution.setVariable("accountNumber", request.accountNumber)
    }
}
