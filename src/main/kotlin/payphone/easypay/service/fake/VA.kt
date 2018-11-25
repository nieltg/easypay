package payphone.easypay.service.fake

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.delegate.DelegateExecution
import payphone.easypay.service.fake.common.qr.ActivationServlet
import payphone.easypay.service.fake.common.qr.QRViewServlet
import java.net.HttpURLConnection
import java.net.URL
import javax.ejb.Stateless
import javax.inject.Inject
import javax.inject.Named
import javax.servlet.annotation.WebServlet

@WebServlet(urlPatterns = ["/va/activation"])
class VAActivationServlet : ActivationServlet(messageName = "va-activation")

@Stateless
@Named("vaService")
open class VAService {
    @Inject
    lateinit var runtimeService: RuntimeService
    fun sendConfirmation(execution: DelegateExecution){
       /* var callbackUrl:String = execution.getVariable("callbackUrl") as String
        var con: HttpURLConnection = URL(callbackUrl).openConnection() as HttpURLConnection
        con.requestMethod = "GET"

        var responseCode:Int = con.responseCode
        if(responseCode == 200){
            return
        }
        else{
            throw Exception("can't connect to callback url")
        }*/
        runtimeService = execution.processEngineServices.runtimeService

        runtimeService.createMessageCorrelation("va-confirmation")
                .processInstanceId(execution.getVariable("id").toString())
                .correlate()
    }
}
