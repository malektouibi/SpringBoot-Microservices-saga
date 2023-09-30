package com.massanalytics.coreapis.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CartUpdatedEvent {
    private String cartId;
    private String cartStatus;
}
