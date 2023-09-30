package com.massanalytics.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String cartId;
    private String email;
    private String itemId;
    private int quantity;
    private String cartStatus;
}
