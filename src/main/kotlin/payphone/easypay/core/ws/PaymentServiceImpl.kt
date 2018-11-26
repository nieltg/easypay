package payphone.easypay.core.ws

import org.camunda.bpm.engine.RuntimeService
import payphone.easypay.core.entity.PaymentEvent
import payphone.easypay.core.entity.PaymentEvent_
import payphone.easypay.core.entity.PaymentRequest
import java.util.*
import javax.inject.Inject
import javax.jws.WebService
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@WebService(
        serviceName = "PaymentService", name = "PaymentService", portName = "payment",
        endpointInterface = "payphone.easypay.core.ws.PaymentService")
open class PaymentServiceImpl : PaymentService {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Inject
    lateinit var runtimeService: RuntimeService

    override fun getPaymentMethods(): List<PaymentMethod> = listOf(
            PaymentMethod(paymentMethodId = "ovo", name = "OVO"),
            PaymentMethod(paymentMethodId = "go_pay", name = "GO-PAY"),
            PaymentMethod(paymentMethodId = "bank_va", name = "Transfer to Virtual Account"),
            PaymentMethod(paymentMethodId = "bank", name = "Transfer to Account (unique number)"))

    @Transactional
    override fun beginPayment(request: PaymentRequest): String {
        entityManager.persist(request)
        entityManager.flush()

        val paymentId = request.paymentId.toString()

        runtimeService.createMessageCorrelation("begin-payment")
                .processInstanceBusinessKey(paymentId)
                .setVariable("amount", request.amount)
                .setVariable("paymentMethodId", request.paymentMethodId)
                .correlateStartMessage()

        return paymentId
    }

    override fun getPaymentEvents(paymentId: String, lastEventId: Long?): PaymentEventsBlock {
        val builder = entityManager.criteriaBuilder
        val criteria = builder.createQuery(PaymentEvent::class.java)

        val entity = criteria.from(PaymentEvent::class.java)

        val eventIdAttr = entity[PaymentEvent_.paymentEventId]
        val requestAttr = entity[PaymentEvent_.paymentRequest]

        criteria.select(entity)
        criteria.orderBy(builder.asc(eventIdAttr))

        var whereClause = builder.equal(
                requestAttr,
                entityManager.getReference(PaymentRequest::class.java, UUID.fromString(paymentId)))
        if (lastEventId != null)
            whereClause = builder.and(whereClause, builder.gt(eventIdAttr, lastEventId))
        criteria.where(whereClause)

        val results = entityManager.createQuery(criteria).resultList
        return PaymentEventsBlock(
                events = results,
                lastEventId = results.lastOrNull()?.paymentEventId ?: lastEventId)
    }
}
