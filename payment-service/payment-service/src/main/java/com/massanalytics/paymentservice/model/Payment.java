package com.massanalytics.paymentservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @Column(name = "paymentId")
    private String paymentId;
    private String cartId;
    private String cardNumber;
    private String cvv2;
}
