package com.example.marketplace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SellerOrder extends Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Account seller;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    @ManyToOne
    @JsonIgnore
    private BuyerOrder buyerOrder;
}
