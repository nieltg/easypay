package payphone.easypay.core.entity

import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class PaymentRequest(
        @Id @GeneratedValue var paymentId: UUID? = null,

        @Column(nullable = false) var paymentMethodId: String? = null,
        @Column(nullable = false) var amount: BigDecimal? = null)
