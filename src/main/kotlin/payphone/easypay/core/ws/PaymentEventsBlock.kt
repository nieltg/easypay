package payphone.easypay.core.ws

import payphone.easypay.core.entity.PaymentEvent
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
data class PaymentEventsBlock(var events: List<PaymentEvent>, var lastEventId: Long?) {
    constructor() : this(events = emptyList(), lastEventId = null)
}
