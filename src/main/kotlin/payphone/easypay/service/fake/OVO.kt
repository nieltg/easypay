package payphone.easypay.service.fake

import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.service.fake.common.qr.ActivationServlet
import payphone.easypay.service.fake.common.qr.QRImageServlet
import payphone.easypay.service.fake.common.qr.QRViewServlet
import payphone.easypay.ws.PaymentMethod
import payphone.easypay.ws.PaymentRequest
import payphone.easypay.ws.PaymentService
import payphone.easypay.ws.PaymentStatus
import uk.org.okapibarcode.backend.Pdf417
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Future
import javax.ejb.Stateless
import javax.inject.Named
import javax.jws.WebMethod
import javax.jws.WebService
import javax.servlet.annotation.WebServlet
import javax.swing.text.html.HTML
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.namespace.QName
import javax.xml.ws.AsyncHandler
import javax.xml.ws.Service

@WebServlet(urlPatterns = ["/ovo/activation"])
class OVOActivationServlet : ActivationServlet(messageName = "ovo-activation")

@WebServlet(urlPatterns = ["/ovo/qr"])
class OVOQRImageServlet : QRImageServlet(
        qrClass = Pdf417::class.java, zoomFactor = 4, activationUrlPart = "/ovo/activation")

@WebServlet(urlPatterns = ["/ovo"])
class OVOQRViewServlet : QRViewServlet(jspPath = "/WEB-INF/jsp/ovo.jsp")

@Stateless
@Named("ovoService")
open class OVOService {
    fun beginValidation(execution: DelegateExecution) {
        //execution.setVariable("qrId", execution.processInstanceId)
        //println("PROCESS INSTANCE ID: ${execution.processInstanceId}")


        //val url = URL("http://localhost:9080/PaymentService.wsdl")
        //val qname = QName("http://ws.easypay.payphone/", "PaymentService")

        //val service = Service.create(url, qname)
        //println("Service is created.")
        //val api = service.getPort(PaymentService::class.java)

        //val request = BeginValidationResponse()
        val qrurl:String = "http://167.205.35.211:8080/easypay/ovo/qr?c="+execution.processInstanceId

        //request.url = qrurl
        //api.beginPayment(request)

        println(qrurl)
    }
    fun sendConfirmation(execution: DelegateExecution){
        var callbackUrl:String = execution.getVariable("callbackUrl") as String
        var con:HttpURLConnection = URL(callbackUrl).openConnection() as HttpURLConnection
        con.requestMethod = "GET"

        var responseCode:Int = con.responseCode
        if(responseCode == 200){
            return
        }
        else{
            throw Exception("can't connect to callback url")
        }
    }
}
