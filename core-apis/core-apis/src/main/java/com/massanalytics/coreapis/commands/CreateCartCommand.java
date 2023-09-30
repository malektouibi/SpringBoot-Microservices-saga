package com.massanalytics.coreapis.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CreateCartCommand {
    @TargetAggregateIdentifier
    private String cartId;
    private String email;
    private String itemId;
    private int quantity;
    private String cartStatus;
}
