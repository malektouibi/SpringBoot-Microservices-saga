package com.massanalytics.cartservice.dto;

import java.util.Optional;

public class CartUpdate extends CartRequest{
    private Optional<String> email;
    private Optional<String> item;
    private Optional<Integer> quantity;
}
