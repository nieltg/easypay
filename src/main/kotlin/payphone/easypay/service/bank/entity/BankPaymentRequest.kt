package payphone.easypay.service.bank.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class BankPaymentRequest(
        @Id var paymentId: String? = null,
        @Column(unique = true) var amount: BigDecimal? = null)
