package com.example.marketplace.dao;

import com.example.marketplace.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepo extends JpaRepository<Conversation,Long> {
    public Optional<Conversation> findByBuyer_IdAndSeller_Id(Long buyerId,Long sellerId);
    public List<Conversation> findAllByBuyer_IdOrSeller_Id(Long buyerId,Long sellerId);

    public Conversation save(Conversation conversation);

    List<Conversation> findAllByBuyer_IdOrSeller_IdOrderByLastModifiedDesc(Long id, Long id1);
}
