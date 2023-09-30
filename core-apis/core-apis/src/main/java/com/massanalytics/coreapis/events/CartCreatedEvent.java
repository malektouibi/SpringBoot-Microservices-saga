package com.massanalytics.coreapis.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CartCreatedEvent {
    private String cartId;
    private String email;
    private String itemId;
    private int quantity;
    private String cartStatus;
}
