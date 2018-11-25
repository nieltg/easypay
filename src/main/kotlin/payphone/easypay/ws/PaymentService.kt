package payphone.easypay.ws

import org.camunda.bpm.engine.delegate.DelegateExecution
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Future
import javax.ejb.Stateless
import javax.inject.Named
import javax.jws.WebMethod
import javax.jws.WebService
import javax.xml.bind.annotation.*
import javax.xml.namespace.QName
import javax.xml.ws.AsyncHandler
import javax.xml.ws.Service

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

@Stateless
@Named("paymentService")
open class PaymentServiceEasyPay {

    fun correlateId(execution: DelegateExecution) {
        //var em:EntityManager = emf.createEntityManager()
        //var id:String = execution.getVariable("id") as String

        //var transaction:Transaction = em.find(Transaction::class.java, id)
        //transaction.firstProcessInstanceId = execution.processInstanceId
    }

    fun initOvo(execution: DelegateExecution) {
    }

    fun initGoPay(execution: DelegateExecution) {
    }

    fun initVA(execution: DelegateExecution) {
    }

    fun initBank(execution: DelegateExecution) {
    }
}