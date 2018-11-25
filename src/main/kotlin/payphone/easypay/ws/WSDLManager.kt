package payphone.easypay.ws

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(urlPatterns = ["/wsdl"])
class WSDLManager : HttpServlet(){
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        //val file = WSDLManager::class.java.getResource("/wsdl/"+req.toString()).readText()
        //super.doGet(req, resp)
        if(WSDLManager::class.java.getResource("/wsdl/"+req.getParameter("name") + ".wsdl") != null){

            resp.contentType = "text/plain"
            resp.setHeader("Content-disposition", "attachment; filename=" + req.getParameter("name") + ".wsdl")

            resp.writer.write(WSDLManager::class.java.getResource("/wsdl/"+req.getParameter("name") + ".wsdl").readText())
            /*
            req.servletContext.getResourceAsStream("/wsdl/" + req.getParameter("name") + ".wsdl").use { `in` ->
                resp.outputStream.use { out ->

                    val buffer = ByteArray(1024)

                    var numBytesRead: Int = `in`.read(buffer)
                    while (numBytesRead > 0) {
                        out.write(buffer, 0, numBytesRead)
                        numBytesRead = `in`.read(buffer)
                    }
                }
            }
            */
        }


    }
}