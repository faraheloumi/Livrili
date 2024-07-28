package com.example.marketplace.dao;

import com.example.marketplace.model.BuyerOrder;
import com.example.marketplace.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyerOrderRepo extends JpaRepository<BuyerOrder,Long> {
    List<BuyerOrder> findAllByBuyer_Id(Long id);
}
