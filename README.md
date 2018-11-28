# EasyPay

**EasyPay** is a toy payment service made for IF4050 Service-Oriented Software Development course. **EasyPay** works on the top of JBoss Wildfly which provides Java EE platform, and uses Camunda BPM as the business process engine.

## Usage

**EasyPay** can be used by another services to enhance them with payment feature.

1. Get available payment methods supported by this service.
2. Request a payment process.
3. Listen for events which will be happened for a single payment process.

## Features

### Payment with 'OVO'

![Payment Barcode](doc/Screenshot_2018-11-28%20OVO%20Payment%20Barcode.png)

Payment with 'OVO' is a payment method which requires customer to scan the specified barcode. Users of this service will be informed about the URL which will open a page containing the specified barcode.

### Payment with 'GO-PAY'

![Payment QR Code](doc/Screenshot_2018-11-28%20GO-PAY%20Payment%20QR%20Code.png)

Payment with 'GO-PAY' is a payment method which requires customer to scan the specified QR code. Users of this service will be informed about the URL which will open a page containing the specified QR code.

### Transfer to Virtual Account

![Virtual Account Payment Console](doc/Screenshot_2018-11-28%20Virtual%20Account%20Payment%20Console.png)

Transfer to Virtual Account is a payment method which requires customer to 'transfer' an amount to specified virtual account number. The *transfer* operation is simulated by entering account number and paid amount in Virtual Account Payment Console, which is available at `/easypay/va`. Users of this service will be informed about the generated account number itself.

### Transfer to Account (unique number)

![Bank Payment Console](doc/Screenshot_2018-11-28%20Bank%20Payment%20Console.png)

Transfer to Account is a payment method which requires customer to 'transfer' an amount which has been marked up for identification purpose. The *transfer* operation is simulated by entering the paid amount in Bank Payment Console, which is available at `/easypay/bank`. Users of this service will be informed about the generated amount.

## Integration

**EasyPay** uses SOAP as its primary communication protocol. Users can generate the classes used for communication using WSDL provided by the service which is located at `/easypay/PaymentService?wsdl`.

An example of client service can be found here, at [LazuliSound/easypay-client](https://github.com/LazuliSound/easypay-client).

### API

#### `getPaymentMethods(): List<PaymentMethod>`

This method returns a list of `PaymentMethod` objects, containing `paymentMethodId: String` and `name: String` property, which represents available payment methods that can be done using this service.

This is an example of possible output:

| paymentMethodId |                 name                |
|:---------------:|:-----------------------------------:|
| ovo             | OVO                                 |
| go_pay          | GO-PAY                              |
| bank            | Transfer to Account (unique number) |
| bank_va         | Transfer to Virtual Account         |

#### `beginPayment(paymentMethodId: String, amount: BigDecimal): String`

This method starts a new payment process from provided `paymentMethodId` and `amount`. This method returns newly created `paymentId: String` for event listening and more additional purposes, maybe, in the future.

#### `getPaymentEvents(paymentId: String, lastEventId: Long?): PaymentEventsBlock`

This method queries list of events which is happened in the specified payment process which is referenced by provided `paymentId`. This method returns an object, containing `events: List<PaymentEvent>` and `lastEventId`. Users can prevent this method for returning already queried events by specifying received `lastEventId` from previous method call to this method. **Note:** Set `lastEventId` to `null` or `0` at the first call of this method.

The `PaymentEvent` object which has been mentioned before contains `type: PaymentEventType` which is an enumeration type, and additional fields which will be explained later. Users can process the events based of its type.

##### `SUCCESS` Type

This event states that the specified payment process has been successfully done. The final received amount is specified on `amount: BigDecimal` property of `PaymentEvent` object. No more event will be received after receiving this event.

##### `FAILURE` Type

This event states that the specified payment process has just been failed. The reason why the payment fails is specified on `reason: String` property of `PaymentEvent` object. No more event will be received after receiving this event.

##### `OPEN_URL` Type

This event states that an URL should be provided to the customer to continue the payment process. The URL itself is provided on `urlToOpen: String` property of `PaymentEvent` object.

##### `AMOUNT_CHANGED` Type

This event states that the payment amount has been modified for uniqueness checking. The new payment amount itself should be provided to the customer to continue the payment process. The new payment amount is provided on `amount: BigDecimal` property of `PaymentEvent` object.

##### `ACCOUNT_NUMBER_AVAILABLE` Type

This event states that an account number has been available to be provided to the customer to continue the payment process. The account number itself is provided on `accountNumber: String` property of `PaymentEvent` object.

## Credits

**EasyPay** is bought to you by **payphone** team.

- 13515071 Daniel Pintara ([nieltg](https://github.com/nieltg))
- 13515136 Lazuardi Firdaus ([LazuliSound](https://github.com/LazuliSound))
- 18215036 	Ahmad Fawwaz Zuhdi ([18215036](https://github.com/18215036))
