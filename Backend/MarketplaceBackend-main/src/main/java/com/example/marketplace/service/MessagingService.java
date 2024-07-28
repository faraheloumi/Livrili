package com.example.marketplace.service;

import com.example.marketplace.dao.CategoryRepo;
import com.example.marketplace.dao.ConversationRepo;
import com.example.marketplace.dao.MessageRepo;
import com.example.marketplace.model.Account;
import com.example.marketplace.model.Category;
import com.example.marketplace.model.Conversation;
import com.example.marketplace.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessagingService {
    @Autowired
    MessageRepo messageDao;
    @Autowired
    ConversationRepo conversationDao;
    public Conversation getOrCreateConversation(Long buyerId,Long sellerId){
        Optional<Conversation> conversation= conversationDao.findByBuyer_IdAndSeller_Id(buyerId,sellerId);
        if(conversation.isPresent()){
            return conversation.get();
        }else{
            conversation= conversationDao.findByBuyer_IdAndSeller_Id(sellerId,buyerId);
            if (conversation.isPresent()){
                return conversation.get();
            }else {
                Conversation newConversation=new Conversation();
                Account buyer=new Account();buyer.setId(buyerId);
                Account seller=new Account();seller.setId(sellerId);
                newConversation.setBuyer(buyer);
                newConversation.setSeller(seller);
                return conversationDao.save(newConversation);
            }
        }

    }
    public void saveMessage(Message message){
        Conversation conversation=conversationDao.findById(message.getConversation().getId()).orElse(null);
        if(conversation!=null){
            messageDao.save(message);
            conversation.setLastModified(new Date());
            conversationDao.save(conversation);
        }

    }
    public List<Conversation> getConversationsByUserId(Long id){
        return conversationDao.findAllByBuyer_IdOrSeller_IdOrderByLastModifiedDesc(id,id);
    }
}