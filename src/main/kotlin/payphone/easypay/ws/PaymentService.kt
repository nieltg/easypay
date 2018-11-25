package payphone.easypay.ws

import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.service.fake.*
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

    @WebMethod fun initOvo(request: OvoRequest): String

    @WebMethod fun initGoPay(request: GoPayRequest): String

    @WebMethod fun initVa(request: VaRequest): String

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

        val url = URL("http://167.205.35.211:8080/easypay/wsdl?name=PaymentService")
        val qname = QName("http://ws.easypay.payphone/", "PaymentService")

        val service = Service.create(url, qname)
        val api = service.getPort(PaymentService::class.java)

        val request = OvoRequest()
        request.amount = execution.getVariable("paymentAmount") as BigDecimal
        request.id = execution.processInstanceId
        api.initOvo(request)
    }

    fun initGoPay(execution: DelegateExecution) {

        val url = URL("http://167.205.35.211:8080/easypay/wsdl?name=PaymentService")
        val qname = QName("http://ws.easypay.payphone/", "PaymentService")

        val service = Service.create(url, qname)
        val api = service.getPort(PaymentService::class.java)

        val request = GoPayRequest()
        request.amount = execution.getVariable("paymentAmount") as BigDecimal
        request.id = execution.processInstanceId
        api.initGoPay(request)
    }

    fun initVA(execution: DelegateExecution) {
        val url = URL("http://167.205.35.211:8080/easypay/wsdl?name=PaymentService")
        val qname = QName("http://ws.easypay.payphone/", "PaymentService")

        val service = Service.create(url, qname)
        val api = service.getPort(PaymentService::class.java)

        val request = VaRequest()
        request.amount = execution.getVariable("paymentAmount") as BigDecimal
        request.id = execution.processInstanceId
        api.initVa(request)
    }

    fun initBank(execution: DelegateExecution) {
    }

    fun requestOvo(execution: DelegateExecution){
        val url = URL("http://167.205.35.211:8080/easypay/wsdl?name=FakeOvoService")
        val qname = QName("http://fake.service.easypay.payphone/", "FakeOvoService")

        val service = Service.create(url, qname)
        val api = service.getPort(FakeOvoService::class.java)

        val request:OvoRequest = OvoRequest()
        request.amount = execution.getVariable("amount") as BigDecimal
        request.id = execution.processInstanceId
        request.callbackURL = "http://google.com"
        api.beginPayment(request)
    }

    fun sendOvoQr(execution: DelegateExecution){
    }

    fun sendOvoConfirmation(execution: DelegateExecution){
        var runtimeService = execution.processEngineServices.runtimeService

        runtimeService.createMessageCorrelation("ovo-respond")
                .processInstanceId(execution.getVariable("id").toString())
                .correlate()
    }

    fun requestGoPay(execution: DelegateExecution){
        val url = URL("http://167.205.35.211:8080/easypay/wsdl?name=FakeGoPayService")
        val qname = QName("http://fake.service.easypay.payphone/", "FakeGoPayService")

        val service = Service.create(url, qname)
        val api = service.getPort(FakeGoPayService::class.java)

        val request:GoPayRequest = GoPayRequest()
        request.amount = execution.getVariable("amount") as BigDecimal
        request.id = execution.processInstanceId
        request.callbackURL = "http://google.com"
        api.begingopayPayment(request)
    }

    fun sendGoPayQr(execution: DelegateExecution){
    }

    fun sendGoPayConfirmation(execution: DelegateExecution){
        var runtimeService = execution.processEngineServices.runtimeService

        runtimeService.createMessageCorrelation("go-pay-respond")
                .processInstanceId(execution.getVariable("id").toString())
                .correlate()
    }

    fun requestVa(execution: DelegateExecution){
        val url = URL("http://167.205.35.211:8080/easypay/wsdl?name=FakeVaService")
        val qname = QName("http://fake.service.easypay.payphone/", "FakeVaService")

        val service = Service.create(url, qname)
        val api = service.getPort(FakeVaService::class.java)

        val request:VaRequest = VaRequest()
        request.amount = execution.getVariable("amount") as BigDecimal
        request.id = execution.processInstanceId
        request.callbackURL = "http://google.com"
        api.beginVaPayment(request)
    }

    fun sendVaQr(execution: DelegateExecution){
    }

    fun sendVaConfirmation(execution: DelegateExecution){
        var runtimeService = execution.processEngineServices.runtimeService

        runtimeService.createMessageCorrelation("va-respond")
                .processInstanceId(execution.getVariable("id").toString())
                .correlate()
    }
}

