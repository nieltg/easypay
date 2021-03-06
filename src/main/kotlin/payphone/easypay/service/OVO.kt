package payphone.easypay.service

import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.service.common.qr.ActivationServlet
import payphone.easypay.service.common.qr.QRImageServlet
import payphone.easypay.service.common.qr.QRViewServlet
import uk.org.okapibarcode.backend.Pdf417
import javax.annotation.ManagedBean
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

@ManagedBean
@Named("ovoToolkit")
open class OVOToolkit {
    fun requestBarcodeUrl(execution: DelegateExecution) {
        execution.setVariable("urlToOpen", "http://167.205.35.211:8080/easypay/ovo?c=${execution.processInstanceId}")

    }
}
