package payphone.easypay.core.entity

import org.jetbrains.annotations.Nullable
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.xml.bind.annotation.*

@Entity
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.NONE)
data class PaymentEvent(
        @XmlAttribute @Id @GeneratedValue var paymentEventId: Long? = null,
        @ManyToOne var paymentRequest: PaymentRequest,

        @XmlAttribute var type: PaymentEventType,
        // SUCCESS, AMOUNT_CHANGED
        @XmlAttribute var amount: BigDecimal? = null,
        // FAILURE
        @XmlAttribute var reason: String? = null,
        // OPEN_URL
        @XmlAttribute var urlToOpen: String? = null,
        // ACCOUNT_NUMBER_AVAILABLE
        @XmlAttribute var accountNumber: String? = null) {

    constructor() : this(paymentRequest = PaymentRequest(), type = PaymentEventType.SUCCESS)
}
