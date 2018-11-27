package payphone.easypay.service.fake

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class BankPaymentRequest(
        @Id var paymentId:String,
        @Column(unique = true) var amount:BigDecimal?)
{

    constructor() : this(paymentId = "",amount = BigDecimal(0))
}