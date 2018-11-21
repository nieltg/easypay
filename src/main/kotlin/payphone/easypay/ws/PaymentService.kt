package payphone.easypay.ws

import java.math.BigDecimal
import java.util.concurrent.Future
import javax.jws.WebMethod
import javax.jws.WebService
import javax.xml.ws.AsyncHandler

data class PaymentMethod(val paymentMethodId: String, val name: String)

@WebService
interface PaymentService {
    @WebMethod fun getPaymentMethods(): Array<PaymentMethod>

    @WebMethod fun beginPayment(paymentMethodId: String, amount: BigDecimal): String
    @WebMethod fun getPaymentStatus(paymentId: String): PaymentStatus

    @WebMethod fun waitPaymentStatus(paymentId: String, handler: AsyncHandler<PaymentStatus>): Future<Any>
}
