package payphone.easypay.ws

import java.math.BigDecimal
import javax.jws.WebMethod
import javax.jws.WebService

@WebService
interface PaymentService {
    @WebMethod fun getPaymentMethods(): Array<PaymentMethod>

    @WebMethod fun beginPayment(paymentMethodId: String, amount: BigDecimal): String
    @WebMethod fun getPaymentDetail(paymentId: String): PaymentDetail
}
