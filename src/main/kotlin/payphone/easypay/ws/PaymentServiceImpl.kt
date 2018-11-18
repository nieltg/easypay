package payphone.easypay.ws

import java.math.BigDecimal
import javax.jws.WebService

@WebService(
        serviceName = "PaymentService", name = "Payment", portName = "payment",
        endpointInterface = "payphone.easypay.ws.PaymentService")
open class PaymentServiceImpl : PaymentService {
    override fun getPaymentMethods(): Array<PaymentMethod> = emptyArray()

    override fun beginPayment(paymentMethodId: String, amount: BigDecimal): String = ""

    override fun getPaymentDetail(paymentId: String): PaymentDetail = PaymentDetail()
}
