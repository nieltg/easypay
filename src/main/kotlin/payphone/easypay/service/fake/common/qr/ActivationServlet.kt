package payphone.easypay.service.fake.common.qr

import org.camunda.bpm.engine.RuntimeService
import javax.inject.Inject
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class ActivationServlet(val messageName: String) : HttpServlet() {
    @Inject
    lateinit var runtimeService: RuntimeService

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val code = req.getParameter("c")
        if (code == null) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        runtimeService.startProcessInstanceByMessage(
                messageName, mutableMapOf<String, Any>("code" to code))
    }
}
