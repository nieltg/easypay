package payphone.easypay.service.fake

import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.service.fake.common.qr.ActivationServlet
import payphone.easypay.service.fake.common.qr.QRImageServlet
import payphone.easypay.service.fake.common.qr.QRViewServlet
import uk.org.okapibarcode.backend.Pdf417
import javax.ejb.Stateless
import javax.inject.Named
import javax.servlet.annotation.WebServlet

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
        execution.setVariable("qrId", execution.processInstanceId)
        println("PROCESS INSTANCE ID: ${execution.processInstanceId}")
    }
}
