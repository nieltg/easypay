package payphone.easypay.ws

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.history.HistoricProcessInstance
import org.camunda.bpm.engine.runtime.MessageCorrelationResult
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery
import java.net.HttpURLConnection
import java.util.concurrent.Future
import javax.inject.Inject
import javax.jws.WebService
import javax.xml.ws.AsyncHandler

@WebService(
        serviceName = "PaymentService", name = "Payment", portName = "payment",
        endpointInterface = "payphone.easypay.ws.PaymentService")
open class PaymentServiceImpl : PaymentService {
    @Inject
    lateinit var runtimeService: RuntimeService

    @Inject
    lateinit var historyService: HistoryService

    override fun getPaymentMethods(): Array<PaymentMethod> = arrayOf(
            PaymentMethod(paymentMethodId = "ovo", name = "OVO"),
            PaymentMethod(paymentMethodId = "gopay", name = "GO-PAY"),
            PaymentMethod(paymentMethodId = "bank", name = "Transfer ke Virtual Account"),
            PaymentMethod(paymentMethodId = "bank_classic", name = "Transfer Bank (angka unik)"))

    override fun beginPayment(request: PaymentRequest): String {
        val result:MessageCorrelationResult = runtimeService.createMessageCorrelation("payment-request")
                .setVariable("paymentMethod", request.paymentMethodId)
                .setVariable("paymentAmount", request.amount)
                .correlateWithResult()

        return result.getExecution().getProcessInstanceId()
    }

    override fun getPaymentStatus(paymentId: String): PaymentStatus {
        val instance = historyService.createHistoricProcessInstanceQuery().processInstanceId(paymentId).singleResult()
        var status:PaymentStatusValue
        if(instance.getState() == "COMPLETED"){
            status = PaymentStatusValue.OK
        }
        else{
            status = PaymentStatusValue.IN_PROGRESS
        }
        val paymentStatus:PaymentStatus = PaymentStatus(instance.getId(), status, null, null, null)
        return paymentStatus

    }

    override fun waitPaymentStatus(paymentId: String, handler: AsyncHandler<PaymentStatus>): Future<Any> {
        TODO("not implemented")
    }

//    override fun beginPayment(paymentMethodId: String, amount: BigDecimal): String {
//        val processVariables = mutableMapOf<String, Any>(
//                "payment-method-id" to paymentMethodId, "amount" to amount)
//
//        runtimeService.startProcessInstanceByMessage(
//                "payment-request", processVariables)
//
//        return ""
//    }
//
//    override fun getPaymentDetail(paymentId: String): PaymentDetail = PaymentDetail()
}
