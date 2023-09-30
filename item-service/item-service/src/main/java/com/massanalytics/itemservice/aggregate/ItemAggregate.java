package com.massanalytics.itemservice.aggregate;

import com.massanalytics.coreapis.commands.VerifyItemCommand;
import com.massanalytics.coreapis.events.ItemInStockEvent;
import com.massanalytics.coreapis.events.ItemOutOfStockEvent;
import com.massanalytics.itemservice.status.ItemStatus;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@NoArgsConstructor
@Slf4j
public class ItemAggregate {

    @AggregateIdentifier
    private String itemId;
    private String cartId;
    private int quantity;
    private String itemStatus;

    @CommandHandler
    public ItemAggregate(VerifyItemCommand verifyItemCommand) {
        log.info("Item Aggregate - handle VerifyItemCommand");
        //Item item = itemRepository.findById(verifyItemCommand.getItem())
        //        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        //                "Item with Id " + verifyItemCommand.getItem() + " not found"));
        if(verifyItemCommand.getQuantity() > 5) {
            log.info("Item out of stock");
            AggregateLifecycle.apply(new ItemOutOfStockEvent(verifyItemCommand.getItemId(), verifyItemCommand.getCartId(),
                    verifyItemCommand.getQuantity()));
        }
        else {
            log.info("Item in stock");
            //item.setQuantity(item.getQuantity() - verifyItemCommand.getQuantity());
            //log.info("Item's new quantity: " + item.getQuantity());
            //itemRepository.save(item);
            AggregateLifecycle.apply(new ItemInStockEvent(verifyItemCommand.getItemId(), verifyItemCommand.getCartId(),
                    verifyItemCommand.getQuantity()));
        }
    }

    @EventSourcingHandler
    protected void on(ItemInStockEvent itemInStockEvent) {
        //this.itemId = itemInStockEvent.getItemId();

        //I made this because Aggregate identifier should be unique. Shouldn't be done in production.
        this.itemId = UUID.randomUUID().toString();
        this.cartId = itemInStockEvent.getCartId();
        this.quantity = itemInStockEvent.getQuantity();
        this.itemStatus = String.valueOf(ItemStatus.INSTOCK);
        log.info("Item Aggregate - on ItemInStockEvent");
    }

    @EventSourcingHandler
    protected void on(ItemOutOfStockEvent itemOutOfStockEvent){
        //this.itemId = itemOutOfStockEvent.getItemId();

        //I made this because Aggregate identifier should be unique. Shouldn't be done in production.
        this.itemId = UUID.randomUUID().toString();
        this.cartId = itemOutOfStockEvent.getCartId();
        this.quantity = itemOutOfStockEvent.getQuantity();
        this.itemStatus = String.valueOf(ItemStatus.OUTOFSTOCK);
        log.info("Item Aggregate - on ItemOutOfStockEvent");
    }

}
