package payphone.easypay.service.fake.common.qr

import uk.org.okapibarcode.backend.Symbol
import uk.org.okapibarcode.output.Java2DRenderer
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class QRImageServlet(
        private val qrClass: Class<out Symbol>,
        private val zoomFactor: Int, private val activationUrlPart: String) : HttpServlet() {

    private fun buildBaseUrl(req: HttpServletRequest): String {
        return ("${req.scheme}://${req.serverName}"
                + if (req.serverPort == 80 || req.serverPort == 443) "" else ":${req.serverPort}"
                + req.contextPath)
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val code = req.getParameter("c")
        if (code == null) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        val qr = qrClass.newInstance()
        qr.content = "${buildBaseUrl(req)}$activationUrlPart?c=$code"

        val image = BufferedImage(
                qr.width * zoomFactor, qr.height * zoomFactor,  BufferedImage.TYPE_BYTE_GRAY)
        val g2d = image.createGraphics()

        val renderer = Java2DRenderer(g2d, zoomFactor.toDouble(), Color.WHITE, Color.BLACK)
        renderer.render(qr)

        resp.contentType = "image/png"
        ImageIO.write(image, "png", resp.outputStream)
    }
}
