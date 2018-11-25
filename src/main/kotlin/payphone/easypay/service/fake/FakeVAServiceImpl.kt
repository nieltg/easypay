package payphone.easypay.service.fake

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.MessageCorrelationResult
import javax.inject.Inject
import javax.jws.WebService

@WebService(
        serviceName = "FakeVaService", name = "Va", portName = "va",
        endpointInterface = "payphone.easypay.service.fake.FakeVaService")
open class FakeVaServiceImpl:FakeVaService{
    @Inject
    lateinit var runtimeService: RuntimeService

    override fun beginVaPayment(request: VaRequest): String {
        val result: MessageCorrelationResult = runtimeService.createMessageCorrelation("begin-va-payment")
                .setVariable("amount", request.amount)
                .setVariable("callbackUrl", request.callbackURL)
                .setVariable("id", request.id)
                .correlateWithResult()

        return "success"
    }

}