package com.massanalytics.coreapis.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaymentCreatedEvent {
    private String paymentId;
    private String cartId;
    private String cardNumber;
    private String cvv2;
}
