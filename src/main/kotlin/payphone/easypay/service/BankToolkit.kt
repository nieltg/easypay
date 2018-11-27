package payphone.easypay.service

import org.camunda.bpm.engine.delegate.DelegateExecution
import java.math.BigDecimal
import javax.annotation.ManagedBean
import javax.inject.Named
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@ManagedBean
@Named("bankToolkit")
open class BankToolkit {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun beginPayment(execution: DelegateExecution) {
        val paymentId = execution.businessKey
        val amount = execution.getVariable("amount") as BigDecimal

        var generatedAmount = amount + BigDecimal((0 until 1000).random())
        while (!amountIsAvailable(generatedAmount)){
            generatedAmount = amount + BigDecimal((0 until 1000).random())
        }

        val request = BankPaymentRequest(paymentId = paymentId, amount = generatedAmount)

        entityManager.persist(request)
        entityManager.flush()

        execution.setVariable("amount", generatedAmount)
    }

    fun amountIsAvailable(amount: BigDecimal):Boolean{

        val builder = entityManager.criteriaBuilder
        val criteria = builder.createQuery(BankPaymentRequest::class.java)

        val bankRequestEntity = criteria.from(BankPaymentRequest::class.java)
        val amountAttr = bankRequestEntity[BankPaymentRequest_.amount]

        criteria.select(bankRequestEntity)
        criteria.where(builder.equal(amountAttr, amount))

        val result = entityManager.createQuery(criteria).resultList

        return result.size == 0
    }
}