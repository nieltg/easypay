package payphone.easypay.ws

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Transaction(

    @Id
    @GeneratedValue
    val id: Int = 0,
    var firstProcessInstanceId: String? = null,
    var secondProcessInstanceId: String? = null,
    var status: String? = null

    )