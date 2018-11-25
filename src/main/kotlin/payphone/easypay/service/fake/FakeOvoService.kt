package payphone.easypay.service.fake

import java.math.BigDecimal
import javax.jws.WebMethod
import javax.jws.WebService
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class OvoRequest(var amount: BigDecimal? = null, var callbackURL: String? = null, var id: String? = null)

@WebService
interface FakeOvoService {
    @WebMethod
    fun beginPayment(request: OvoRequest): String
}