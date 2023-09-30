package com.massanalytics.cartservice.service;

import com.massanalytics.coreapis.commands.CreateCartCommand;
import com.massanalytics.cartservice.dto.CartRequest;
import com.massanalytics.cartservice.dto.CartResponse;
import com.massanalytics.cartservice.dto.CartUpdate;
import com.massanalytics.cartservice.model.Cart;
import com.massanalytics.cartservice.repository.CartRepository;
import com.massanalytics.cartservice.status.CartStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CommandGateway commandGateway;

    public Object createCart(CartRequest cartRequest) {
        return commandGateway.sendAndWait(new CreateCartCommand(UUID.randomUUID().toString(), cartRequest.getEmail(),
                cartRequest.getItemId(), cartRequest.getQuantity(), String.valueOf(CartStatus.CREATED)));
    }

    public List<CartResponse> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(this::mapToCartResponse).toList();
    }

    public CartResponse getCartById(String id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart with Id " + id + " not found"));
        return mapToCartResponse(cart);
    }

    public CartResponse updateCart(String id, CartUpdate cartUpdate) {
        CartResponse cartResponse = getCartById(id);
        Cart cartToUpdate = mapToCart(cartResponse);
        if(cartUpdate.getEmail() != null) {
            cartToUpdate.setEmail(cartUpdate.getEmail());
        }
        if(cartUpdate.getItemId() != null) {
            cartToUpdate.setItemId(cartUpdate.getItemId());
        }
        if(Objects.equals(cartUpdate.getQuantity(), null)) {
            cartToUpdate.setQuantity(cartUpdate.getQuantity());
        }
        Cart updatedCart = cartRepository.save(cartToUpdate);
        return mapToCartResponse(updatedCart);
    }

    public void deleteCart(String id) {
        getCartById(id);
        cartRepository.deleteById(id);
    }

    private Cart mapToCart(CartResponse cartResponse) {
        return Cart.builder()
                .cartId(cartResponse.getCartId())
                .email(cartResponse.getEmail())
                .itemId(cartResponse.getItemId())
                .quantity(cartResponse.getQuantity())
                .cartStatus(cartResponse.getCartStatus())
                .build();
    }

    private CartResponse mapToCartResponse(Cart cart) {
        return CartResponse.builder()
                .cartId(cart.getCartId())
                .email(cart.getEmail())
                .itemId(cart.getItemId())
                .quantity(cart.getQuantity())
                .cartStatus(cart.getCartStatus())
                .build();
    }
}
