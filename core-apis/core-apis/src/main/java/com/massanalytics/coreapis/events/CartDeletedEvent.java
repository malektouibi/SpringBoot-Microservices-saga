package com.massanalytics.coreapis.events;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CartDeletedEvent {
    private String cartId;
    private String email;
    private String cartStatus;
}
