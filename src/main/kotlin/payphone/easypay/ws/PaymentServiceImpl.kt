package payphone.easypay.ws

import org.camunda.bpm.engine.RuntimeService
import java.math.BigDecimal
import javax.inject.Inject
import javax.jws.WebService

@WebService(
        serviceName = "PaymentService", name = "Payment", portName = "payment",
        endpointInterface = "payphone.easypay.ws.PaymentService")
open class PaymentServiceImpl : PaymentService {
    @Inject
    lateinit var runtimeService: RuntimeService

    override fun getPaymentMethods(): Array<PaymentMethod> = emptyArray()

    override fun beginPayment(paymentMethodId: String, amount: BigDecimal): String {
        val processVariables = mutableMapOf<String, Any>(
                "payment-method-id" to paymentMethodId, "amount" to amount)

        runtimeService.startProcessInstanceByMessage(
                "payment-request", processVariables)

        return ""
    }

    override fun getPaymentDetail(paymentId: String): PaymentDetail = PaymentDetail()
}
