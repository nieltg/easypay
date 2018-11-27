package payphone.easypay.core.ws

import java.math.BigDecimal
import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebService

@WebService
interface PaymentService {
    @WebMethod fun getPaymentMethods(): List<PaymentMethod>

    @WebMethod fun beginPayment(
            @WebParam(name = "paymentMethodId") paymentMethodId: String,
            @WebParam(name = "amount") amount: BigDecimal): String

    @WebMethod fun getPaymentEvents(
            @WebParam(name = "paymentId") paymentId: String,
            @WebParam(name = "lastEventId") lastEventId: Long?): PaymentEventsBlock
}
