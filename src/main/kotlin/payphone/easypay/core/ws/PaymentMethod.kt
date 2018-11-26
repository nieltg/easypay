package payphone.easypay.core.ws

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
data class PaymentMethod(var paymentMethodId: String, var name: String) {
    constructor() : this(paymentMethodId = "", name = "")
}
