package com.massanalytics.cartservice.controller;

import com.massanalytics.cartservice.dto.CartRequest;
import com.massanalytics.cartservice.dto.CartResponse;
import com.massanalytics.cartservice.dto.CartUpdate;
import com.massanalytics.cartservice.model.Cart;
import com.massanalytics.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCart(@RequestBody CartRequest cartRequest) {
        cartService.createCart(cartRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public CartResponse getCartById(@PathVariable String id) {
        return cartService.getCartById(id);
    }

    @PatchMapping("/{id}")
    public CartResponse updateCart(@PathVariable String id, @RequestBody CartUpdate cartUpdate) {
        return cartService.updateCart(id, cartUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCart(@PathVariable String id) {
        cartService.deleteCart(id);
    }
}
