package com.example.marketplace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String image;
    private Date modifiedOn= new Date();
    @OneToMany(mappedBy = "buyer")
    @JsonIgnore
    private List<BuyerOrder> buyerOrders;
    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    private List<SellerOrder> sellerOrders;
    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    private List<Product> products;
    @OneToMany(mappedBy = "buyer")
    @JsonIgnore
    private List<Conversation> buyerConversations;
    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    private List<Conversation> sellerConversations;
}
