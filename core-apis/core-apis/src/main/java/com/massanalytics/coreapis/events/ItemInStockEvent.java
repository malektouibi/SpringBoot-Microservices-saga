package com.massanalytics.coreapis.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ItemInStockEvent {
    private String itemId;
    private String cartId;
    private int quantity;
}
