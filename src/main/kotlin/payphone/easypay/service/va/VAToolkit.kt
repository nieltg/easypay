package payphone.easypay.service

import org.camunda.bpm.engine.delegate.DelegateExecution
import java.math.BigDecimal
import javax.annotation.ManagedBean
import javax.inject.Named
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@ManagedBean
@Named("vaToolkit")
class VAToolkit {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun beginPayment(execution: DelegateExecution) {
        val paymentId = execution.businessKey
        val amount = execution.getVariable("amount") as BigDecimal

        val request = VAPaymentRequest(paymentId = paymentId, targetAmount = amount)

        entityManager.persist(request)
        entityManager.flush()

        execution.setVariable("accountNumber", request.accountNumber)
    }
}
