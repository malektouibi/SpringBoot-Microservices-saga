package com.massanalytics.paymentservice.aggregate;

import com.massanalytics.coreapis.commands.CreatePaymentCommand;
import com.massanalytics.coreapis.events.CartCreatedEvent;
import com.massanalytics.coreapis.events.PaymentCreatedEvent;
import com.massanalytics.paymentservice.repository.PaymentRepository;
import com.massanalytics.paymentservice.status.PaymentStatus;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

@Aggregate
@NoArgsConstructor
@Slf4j
public class PaymentAggregate {
    @Autowired
    private PaymentRepository paymentRepository;

    @AggregateIdentifier
    private String paymentId;
    private String cartId;
    private String cardNumber;
    private String cvv2;
    private String paymentStatus;

    @CommandHandler
    public PaymentAggregate(CreatePaymentCommand createPaymentCommand) {
        log.info("Payment Aggregate - handle CreatePaymentCommand");
        AggregateLifecycle.apply(new PaymentCreatedEvent(createPaymentCommand.getPaymentId(), createPaymentCommand.getCartId(),
                createPaymentCommand.getCardNumber(), createPaymentCommand.getCvv2()));
    }

    @EventSourcingHandler
    protected void on(PaymentCreatedEvent paymentCreatedEvent){
        this.paymentId = paymentCreatedEvent.getPaymentId();
        this.cartId = paymentCreatedEvent.getCartId();
        this.cardNumber = paymentCreatedEvent.getCardNumber();
        this.cvv2 = paymentCreatedEvent.getCvv2();
        this.paymentStatus = String.valueOf(PaymentStatus.PAID);
    }
}
