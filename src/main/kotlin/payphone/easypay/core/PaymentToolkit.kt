package payphone.easypay.core

import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.core.entity.PaymentEvent
import payphone.easypay.core.entity.PaymentEventType
import payphone.easypay.core.entity.PaymentRequest
import java.math.BigDecimal
import java.util.*
import javax.ejb.Stateless
import javax.inject.Named
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
@Named
open class PaymentToolkit {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun pushEvent(execution: DelegateExecution) {
        val eventType = PaymentEventType.valueOf(execution.getVariable(
                "eventType").toString())

        val event = PaymentEvent(
                paymentRequest = entityManager.getReference(
                        PaymentRequest::class.java, UUID.fromString(execution.businessKey)),
                type = eventType)

        when (eventType) {
            PaymentEventType.SUCCESS, PaymentEventType.AMOUNT_CHANGED ->
                event.amount = execution.getVariable("amount") as BigDecimal

            PaymentEventType.FAILURE ->
                event.reason = execution.getVariable("reason").toString()

            PaymentEventType.OPEN_URL ->
                event.urlToOpen = execution.getVariable("urlToOpen").toString()
        }

        entityManager.persist(event)
    }
}
