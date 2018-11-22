package payphone.easypay.ws

import java.math.BigDecimal
import javax.xml.bind.annotation.*

enum class PaymentStatusValue(val value: String) {
    IN_PROGRESS("IN_PROGRESS"),

    PENDING_OPEN_URL("PENDING_OPEN_URL"),

    OK("OK"),
    FAIL("FAIL")
}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class PendingOpenURLExtra(var url: String? = null)

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class PaymentOKExtra(var amount: BigDecimal? = null)

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class PaymentFailExtra(var reason: String? = null)

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class PaymentStatus(
        var paymentId: String? = null,
        var status: PaymentStatusValue? = null,

        var pendingOpenUrlExtra: PendingOpenURLExtra? = null,
        var okExtra: PaymentOKExtra? = null,
        var failExtra: PaymentFailExtra? = null)
