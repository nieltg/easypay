package payphone.easypay.core.entity

enum class PaymentEventType {
    SUCCESS,
    FAILURE,

    OPEN_URL,
    AMOUNT_CHANGED,
    ACCOUNT_NUMBER_AVAILABLE
}
