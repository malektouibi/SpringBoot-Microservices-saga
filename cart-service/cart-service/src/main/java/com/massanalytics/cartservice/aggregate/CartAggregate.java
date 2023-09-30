package com.massanalytics.cartservice.aggregate;

import com.massanalytics.cartservice.model.Cart;
import com.massanalytics.coreapis.commands.CreateCartCommand;
import com.massanalytics.coreapis.commands.UpdateCartCommand;
import com.massanalytics.coreapis.events.CartCreatedEvent;
import com.massanalytics.cartservice.repository.CartRepository;
import com.massanalytics.coreapis.events.CartUpdatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.*;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Aggregate
@NoArgsConstructor
@Slf4j
public class CartAggregate {

    @Autowired
    private CartRepository cartRepository;

    @AggregateIdentifier
    private String cartId;
    private String email;
    private String itemId;
    private int quantity;
    private String cartStatus;

    @CommandHandler
    public CartAggregate(CreateCartCommand createCartCommand){
        log.info("Cart Aggregate - handle CreateCartCommand");
        AggregateLifecycle.apply(new CartCreatedEvent(createCartCommand.getCartId(), createCartCommand.getEmail(),
                createCartCommand.getItemId(), createCartCommand.getQuantity(), createCartCommand.getCartStatus()));
    }

    @CommandHandler
    public void on(UpdateCartCommand updateCartCommand){
        log.info("Cart Aggregate - handle UpdateCartCommand");
        AggregateLifecycle.apply(new CartUpdatedEvent(updateCartCommand.getCartId(), updateCartCommand.getCartStatus()));
    }

    @EventSourcingHandler
    protected void on(CartCreatedEvent cartCreatedEvent){
        this.cartId = cartCreatedEvent.getCartId();
        this.email = cartCreatedEvent.getEmail();
        this.itemId = cartCreatedEvent.getItemId();
        this.quantity = cartCreatedEvent.getQuantity();
        this.cartStatus = cartCreatedEvent.getCartStatus();
        log.info("Cart Aggregate - on CartCreatedEvent");
    }

    @EventSourcingHandler
    protected void on(CartUpdatedEvent cartUpdatedEvent){
        this.cartStatus = cartUpdatedEvent.getCartStatus();
        log.info("Cart Aggregate - on CartUpdatedEvent");
    }

}
