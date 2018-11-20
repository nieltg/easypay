package payphone.easypay.service.fake.common.qr

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class QRViewServlet(val jspPath: String) : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val code = req.getParameter("c")
        if (code == null) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        req.setAttribute("code", code)
        servletContext.getRequestDispatcher(jspPath).forward(req, resp)
    }
}
