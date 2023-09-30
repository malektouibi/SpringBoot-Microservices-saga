package com.massanalytics.coreapis.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class UpdateCartCommand {
    @TargetAggregateIdentifier
    private String cartId;
    private String cartStatus;
}
