package com.example.marketplace.dao;

import com.example.marketplace.model.Brand;
import com.example.marketplace.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message,Long> {
}
