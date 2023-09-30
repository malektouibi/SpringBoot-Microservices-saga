package com.massanalytics.coreapis.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class VerifyItemCommand {
    @TargetAggregateIdentifier
    private String itemId;
    private String cartId;
    private int quantity;
}
