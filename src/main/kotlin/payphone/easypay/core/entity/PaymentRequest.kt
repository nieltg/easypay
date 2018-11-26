package payphone.easypay.core.entity

import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.xml.bind.annotation.*

@Entity
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.NONE)
data class PaymentRequest(
        @Id @GeneratedValue var paymentId: UUID? = null,

        @XmlAttribute var paymentMethodId: String,
        @XmlAttribute var amount: BigDecimal) {

    constructor() : this(paymentId = null, paymentMethodId = "", amount = BigDecimal(0))
}
