package com.massanalytics.cartservice.saga;

import com.massanalytics.cartservice.status.CartStatus;
import com.massanalytics.coreapis.commands.CreatePaymentCommand;
import com.massanalytics.coreapis.commands.UpdateCartCommand;
import com.massanalytics.coreapis.commands.VerifyItemCommand;
import com.massanalytics.coreapis.events.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class CartManagementSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "cartId")
    public void handle(CartCreatedEvent cartCreatedEvent) {
        log.info("Saga - handle CartCreatedEvent");
        SagaLifecycle.associateWith("itemId", cartCreatedEvent.getItemId());
        commandGateway.sendAndWait(new VerifyItemCommand(cartCreatedEvent.getItemId(), cartCreatedEvent.getCartId(),
                cartCreatedEvent.getQuantity()));
        log.info("Saga continued");
    }

    @SagaEventHandler(associationProperty = "itemId")
    public void handle(ItemInStockEvent itemInStockEvent) {
        log.info("Saga - handle ItemInStockEvent");
        String paymentId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("paymentId", paymentId);
        commandGateway.send(new CreatePaymentCommand(paymentId, itemInStockEvent.getCartId(),
                "ABC123", "123"));
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(PaymentCreatedEvent paymentCreatedEvent) {
        log.info("Saga - handle PaymentCreatedEvent");
        commandGateway.send(new UpdateCartCommand(paymentCreatedEvent.getCartId(), String.valueOf(CartStatus.PAID)));
    }

    @SagaEventHandler(associationProperty = "cartId")
    public void handle(ItemOutOfStockEvent itemOutOfStockEvent) {
        log.info("Saga - handle ItemOutOfStockEvent");
        commandGateway.send(new UpdateCartCommand(itemOutOfStockEvent.getCartId(), String.valueOf(CartStatus.REJECTED)));
    }

    @SagaEventHandler(associationProperty = "cartId")
    public void handle(CartUpdatedEvent cartUpdatedEvent) {
        log.info("Saga - handle CartUpdatedEvent - End of Saga");
        SagaLifecycle.end();
    }

}
