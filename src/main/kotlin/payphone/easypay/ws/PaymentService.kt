package payphone.easypay.ws

import java.math.BigDecimal
import java.util.concurrent.Future
import javax.jws.WebMethod
import javax.jws.WebService
import javax.xml.bind.annotation.*
import javax.xml.ws.AsyncHandler

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class PaymentMethod(var paymentMethodId: String? = null, var name: String? = null)

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class PaymentRequest(var paymentMethodId: String? = null, var amount: BigDecimal? = null)

@WebService
interface PaymentService {
    @WebMethod fun getPaymentMethods(): Array<PaymentMethod>

    @WebMethod fun beginPayment(request: PaymentRequest): String
    @WebMethod fun getPaymentStatus(paymentId: String): PaymentStatus

    @WebMethod fun waitPaymentStatus(paymentId: String, handler: AsyncHandler<PaymentStatus>): Future<Any>
}
