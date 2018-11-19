package payphone.easypay.service.fake.ovo

import uk.org.okapibarcode.backend.Pdf417
import uk.org.okapibarcode.output.Java2DRenderer
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/ovo/qr"])
class QRImageServlet : HttpServlet() {
    companion object {
        const val ZOOM_FACTOR = 4
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val content = req.getParameter("c")
        if (content == null) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        val qr = Pdf417()
        qr.content = content

        val image = BufferedImage(
                qr.width * ZOOM_FACTOR, qr.height * ZOOM_FACTOR,  BufferedImage.TYPE_BYTE_GRAY)
        val g2d = image.createGraphics()

        val renderer = Java2DRenderer(g2d, ZOOM_FACTOR.toDouble(), Color.WHITE, Color.BLACK)
        renderer.render(qr)

        resp.contentType = "image/png"
        ImageIO.write(image, "png", resp.outputStream)
    }
}
