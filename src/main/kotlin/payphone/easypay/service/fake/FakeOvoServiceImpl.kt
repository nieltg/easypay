package payphone.easypay.service.fake

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.MessageCorrelationResult
import payphone.easypay.ws.PaymentService
import javax.inject.Inject
import javax.jws.WebService


@WebService(
        serviceName = "FakeOvoService", name = "Ovo", portName = "ovo",
        endpointInterface = "payphone.easypay.service.fake.FakeOvoService")
open class FakeOvoServiceImpl : FakeOvoService {
    @Inject
    lateinit var runtimeService: RuntimeService

    override fun beginPayment(request: OvoRequest): String {
        val result: MessageCorrelationResult = runtimeService.createMessageCorrelation("begin-ovo-payment")
                .setVariable("amount", request.amount)
                .setVariable("callbackUrl", request.callbackURL)
                .setVariable("id", request.id)
                .correlateWithResult()

        return "success"
    }

}