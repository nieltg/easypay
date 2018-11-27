package payphone.easypay.service.va.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class VAPaymentHistory(
        @Id @GeneratedValue var paymentHistoryId: Long? = null,
        @ManyToOne var paymentRequest: VAPaymentRequest? = null,

        @Column(nullable = false) var amount: BigDecimal? = null)
