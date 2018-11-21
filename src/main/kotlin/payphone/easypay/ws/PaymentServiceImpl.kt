package payphone.easypay.ws

import org.camunda.bpm.engine.RuntimeService
import java.math.BigDecimal
import java.util.concurrent.Future
import javax.inject.Inject
import javax.jws.WebService
import javax.xml.ws.AsyncHandler

@WebService(
        serviceName = "PaymentService", name = "Payment", portName = "payment",
        endpointInterface = "payphone.easypay.ws.PaymentService")
open class PaymentServiceImpl : PaymentService {
    @Inject
    lateinit var runtimeService: RuntimeService

    override fun getPaymentMethods(): Array<PaymentMethod> = arrayOf(
            PaymentMethod(paymentMethodId = "ovo", name = "OVO"),
            PaymentMethod(paymentMethodId = "gopay", name = "GO-PAY"),
            PaymentMethod(paymentMethodId = "bank", name = "Transfer ke Virtual Account"),
            PaymentMethod(paymentMethodId = "bank_classic", name = "Transfer Bank (angka unik)"))

    override fun beginPayment(paymentMethodId: String, amount: BigDecimal): String {
        TODO("not implemented")
    }

    override fun getPaymentStatus(paymentId: String): PaymentStatus {
        TODO("not implemented")
    }

    override fun waitPaymentStatus(paymentId: String, handler: AsyncHandler<PaymentStatus>): Future<Any> {
        TODO("not implemented")
    }

//    override fun beginPayment(paymentMethodId: String, amount: BigDecimal): String {
//        val processVariables = mutableMapOf<String, Any>(
//                "payment-method-id" to paymentMethodId, "amount" to amount)
//
//        runtimeService.startProcessInstanceByMessage(
//                "payment-request", processVariables)
//
//        return ""
//    }
//
//    override fun getPaymentDetail(paymentId: String): PaymentDetail = PaymentDetail()
}
