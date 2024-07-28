package com.example.marketplace.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@MappedSuperclass
public abstract class Order {
    private double totalAmount;
    private OrderStatus status;
    private Date date=new Date();
}
