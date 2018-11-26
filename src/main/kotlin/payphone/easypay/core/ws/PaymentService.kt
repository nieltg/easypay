package payphone.easypay.core.ws

import payphone.easypay.core.entity.PaymentRequest
import javax.jws.WebMethod
import javax.jws.WebService

@WebService
interface PaymentService {
    @WebMethod fun getPaymentMethods(): List<PaymentMethod>

    @WebMethod fun beginPayment(request: PaymentRequest): String
    @WebMethod fun getPaymentEvents(paymentId: String, lastEventId: Long?): PaymentEventsBlock
}
