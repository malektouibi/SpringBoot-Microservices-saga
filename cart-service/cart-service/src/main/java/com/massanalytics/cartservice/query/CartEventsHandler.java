package com.massanalytics.cartservice.query;

import com.massanalytics.coreapis.events.CartCreatedEvent;
import com.massanalytics.cartservice.model.Cart;
import com.massanalytics.cartservice.repository.CartRepository;
import com.massanalytics.coreapis.events.CartUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartEventsHandler {

    private final CartRepository cartRepository;

    @EventHandler
    public void on(CartCreatedEvent cartCreatedEvent) {
        log.info("Cart Events Handler - on CartCreatedEvent");
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartCreatedEvent, cart);
        cartRepository.save(cart);
    }

    @EventHandler
    public void on(CartUpdatedEvent cartUpdatedEvent) {
        log.info("Cart Events Handler - on CartUpdatedEvent");
        Cart cart = cartRepository.findById(cartUpdatedEvent.getCartId())
                .orElseThrow();
        cart.setCartStatus(cartUpdatedEvent.getCartStatus());
        BeanUtils.copyProperties(cart, cart);
        cartRepository.save(cart);
    }

    //@ResetHandler
    //public void reset() {
    //    log.info("****************RESET****************");
    //    cartRepository.deleteAll();
    //}

}
