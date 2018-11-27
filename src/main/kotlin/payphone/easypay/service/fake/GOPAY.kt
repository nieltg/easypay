package payphone.easypay.service.fake

import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.service.fake.common.qr.ActivationServlet
import payphone.easypay.service.fake.common.qr.QRImageServlet
import payphone.easypay.service.fake.common.qr.QRViewServlet
import uk.org.okapibarcode.backend.QrCode
import javax.annotation.ManagedBean
import javax.ejb.Stateless
import javax.inject.Named
import javax.servlet.annotation.WebServlet

@WebServlet(urlPatterns = ["/gopay/activation"])
class GOPAYActivationServlet : ActivationServlet(messageName = "gopay-activation")

@WebServlet(urlPatterns = ["/gopay/qr"])
class GOPAYQRImageServlet : QRImageServlet(
        qrClass = QrCode::class.java, zoomFactor = 8, activationUrlPart = "/gopay/activation")

@WebServlet(urlPatterns = ["/gopay"])
class GOPAYQRViewServlet : QRViewServlet(jspPath = "/WEB-INF/jsp/gopay.jsp")

@ManagedBean
@Named("gopayToolkit")
open class GOPAYService {
    fun requestQrUrl(execution: DelegateExecution) {
        execution.setVariable("urlToOpen", "http://167.205.35.211:8080/easypay/gopay?c=${execution.processInstanceId}")
    }
}
