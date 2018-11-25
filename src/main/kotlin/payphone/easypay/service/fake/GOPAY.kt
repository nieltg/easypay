package payphone.easypay.service.fake

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.service.fake.common.qr.ActivationServlet
import payphone.easypay.service.fake.common.qr.QRImageServlet
import payphone.easypay.service.fake.common.qr.QRViewServlet
import payphone.easypay.ws.PaymentService
import uk.org.okapibarcode.backend.QrCode
import java.net.HttpURLConnection
import java.net.URL
import javax.ejb.Stateless
import javax.inject.Inject
import javax.inject.Named
import javax.servlet.annotation.WebServlet
import javax.xml.namespace.QName
import javax.xml.ws.Service

@WebServlet(urlPatterns = ["/gopay/activation"])
class GOPAYActivationServlet : ActivationServlet(messageName = "gopay-activation")

@WebServlet(urlPatterns = ["/gopay/qr"])
class GOPAYQRImageServlet : QRImageServlet(
        qrClass = QrCode::class.java, zoomFactor = 8, activationUrlPart = "/gopay/activation")

@WebServlet(urlPatterns = ["/gopay"])
class GOPAYQRViewServlet : QRViewServlet(jspPath = "/WEB-INF/jsp/gopay.jsp")

@Stateless
@Named("goPayService")
open class GOPAYService {
    @Inject
    lateinit var runtimeService: RuntimeService
    fun beginValidation(execution: DelegateExecution) {

        //val url = URL("http://localhost:9080/PaymentService.wsdl")
        //val qname = QName("http://ws.easypay.payphone/", "PaymentService")

        //val service = Service.create(url, qname)
        //println("Service is created.")
        //val api = service.getPort(PaymentService::class.java)

        //val request = BeginValidationResponse()
        val qrurl:String = "http://167.205.35.211:8080/easypay/gopay/qr?c="+execution.processInstanceId

        //request.url = qrurl
        //api.beginPayment(request)

        println(qrurl)
    }
    fun sendConfirmation(execution: DelegateExecution){
        //var callbackUrl:String = execution.getVariable("callbackUrl") as String
        //var con: HttpURLConnection = URL(callbackUrl).openConnection() as HttpURLConnection
        //con.requestMethod = "GET"

        //var responseCode:Int = con.responseCode
        //if(responseCode == 200){
        //    return
        //}
        //else{
        //    throw Exception("can't connect to callback url")
        //}
        runtimeService = execution.processEngineServices.runtimeService

        runtimeService.createMessageCorrelation("gopay-confirmation")
                .processInstanceId(execution.getVariable("id").toString())
                .correlate()
    }
}
