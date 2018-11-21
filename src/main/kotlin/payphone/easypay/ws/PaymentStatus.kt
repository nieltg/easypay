package payphone.easypay.ws

import java.math.BigDecimal

enum class PaymentStatusValue(val value: String) {
    IN_PROGRESS("IN_PROGRESS"),

    PENDING_OPEN_URL("PENDING_OPEN_URL"),

    OK("OK"),
    FAIL("FAIL")
}

data class PendingOpenURLExtra(val url: String)
data class PaymentOKExtra(val amount: BigDecimal)
data class PaymentFailExtra(val reason: String)

data class PaymentStatus(
        val paymentId: String,
        val status: PaymentStatusValue,

        val pendingOpenUrlExtra: PendingOpenURLExtra?,
        val okExtra: PaymentOKExtra?,
        val failExtra: PaymentFailExtra?)
