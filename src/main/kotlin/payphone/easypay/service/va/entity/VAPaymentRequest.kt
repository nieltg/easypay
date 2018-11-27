package payphone.easypay.service.va.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class VAPaymentRequest(
        @Id var paymentId: String? = null,

        @Column(unique = true) var accountNumber: String? = null,
        @Column(nullable = false) var amount: BigDecimal? = null)
