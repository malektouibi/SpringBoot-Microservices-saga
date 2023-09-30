package com.massanalytics.coreapis.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CreatePaymentCommand {
    @TargetAggregateIdentifier
    private String paymentId;
    private String cartId;
    private String cardNumber;
    private String cvv2;
}
