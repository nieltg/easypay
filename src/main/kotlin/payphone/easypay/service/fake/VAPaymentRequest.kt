package payphone.easypay.service.fake

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class VAPaymentRequest(
        @Id var paymentId: String,

        @Column(unique=true) var accountNumber: String? = generateAccountNumber(),
        var targetAmount: BigDecimal) {

    companion object {
        fun generateAccountNumber(): String {
            val builder = StringBuilder()

            repeat(10) { builder.append((0..9).random()) }
            return builder.toString()
        }
    }

    constructor() : this(paymentId = "", targetAmount = BigDecimal(0))
}
