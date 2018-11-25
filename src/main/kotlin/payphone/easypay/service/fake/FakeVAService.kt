package payphone.easypay.service.fake

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.MessageCorrelationResult
import java.math.BigDecimal
import javax.inject.Inject
import javax.jws.WebMethod
import javax.jws.WebService
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class VaRequest(var amount: BigDecimal? = null, var callbackURL: String? = null, var id: String? = null)

@WebService
interface FakeVaService {
    @WebMethod
    fun beginVaPayment(request: VaRequest): String
}