package payphone.easypay.core.entity

import java.math.BigDecimal
import javax.persistence.*
import javax.xml.bind.annotation.*

@Entity
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.NONE)
data class PaymentEvent(
        @XmlAttribute @Id @GeneratedValue var paymentEventId: Long? = null,
        @ManyToOne var paymentRequest: PaymentRequest? = null,

        @XmlAttribute @Column(nullable = false) var type: PaymentEventType? = null,
        // SUCCESS, AMOUNT_CHANGED
        @XmlAttribute var amount: BigDecimal? = null,
        // FAILURE
        @XmlAttribute var reason: String? = null,
        // OPEN_URL
        @XmlAttribute var urlToOpen: String? = null,
        // ACCOUNT_NUMBER_AVAILABLE
        @XmlAttribute var accountNumber: String? = null)
