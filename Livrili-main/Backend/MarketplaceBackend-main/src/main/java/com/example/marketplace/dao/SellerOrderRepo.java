package com.example.marketplace.dao;

import com.example.marketplace.model.BuyerOrder;
import com.example.marketplace.model.OrderItem;
import com.example.marketplace.model.SellerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerOrderRepo extends JpaRepository<SellerOrder,Long> {
    List<SellerOrder> findAllBySeller_Id(Long id);

}
