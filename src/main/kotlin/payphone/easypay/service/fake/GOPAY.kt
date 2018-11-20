package payphone.easypay.service.fake

import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.service.fake.common.qr.ActivationServlet
import payphone.easypay.service.fake.common.qr.QRImageServlet
import payphone.easypay.service.fake.common.qr.QRViewServlet
import uk.org.okapibarcode.backend.QrCode
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

@Stateless
@Named
class GOPAYService {
    fun requestPayment(execution: DelegateExecution) {
        val paymentId = execution.getVariable("paymentId").toString()

        execution.setVariable("qrId", paymentId)
    }
}
