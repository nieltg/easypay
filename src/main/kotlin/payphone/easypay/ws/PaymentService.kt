package payphone.easypay.ws

import java.math.BigDecimal
import java.util.concurrent.Future
import javax.jws.WebMethod
import javax.jws.WebService
import javax.xml.ws.AsyncHandler

data class PaymentMethod(val paymentMethodId: String, val name: String)

data class PaymentRequest(val paymentMethodId: String, val amount: BigDecimal)

@WebService
interface PaymentService {
    @WebMethod fun getPaymentMethods(): Array<PaymentMethod>

    @WebMethod fun beginPayment(request: PaymentRequest): String
    @WebMethod fun getPaymentStatus(paymentId: String): PaymentStatus

    @WebMethod fun waitPaymentStatus(paymentId: String, handler: AsyncHandler<PaymentStatus>): Future<Any>
}
