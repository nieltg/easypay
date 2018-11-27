package payphone.easypay.service.fake

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class VAPaymentHistory(
        @Id @GeneratedValue var paymentHistoryId: Long? = null,
        @ManyToOne var paymentRequest: VAPaymentRequest,

        var amount: BigDecimal) {

    constructor() : this(paymentRequest = VAPaymentRequest(), amount = BigDecimal(0))
}
