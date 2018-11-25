package payphone.easypay.service.fake

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.MessageCorrelationResult
import javax.inject.Inject
import javax.jws.WebService


@WebService(
        serviceName = "FakeGoPayService", name = "GoPay", portName = "gopay",
        endpointInterface = "payphone.easypay.service.fake.FakeGoPayService")
open class FakeGoPayServiceImpl : FakeGoPayService {
    @Inject
    lateinit var runtimeService: RuntimeService

    override fun begingopayPayment(request: GoPayRequest): String {
        runtimeService.createMessageCorrelation("begin-gopay-payment")
                .setVariable("amount", request.amount)
                .setVariable("callbackUrl", request.callbackURL)
                .setVariable("id", request.id)
                .correlate()

        return "success"
    }

}