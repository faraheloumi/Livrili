package com.example.marketplace.service;


import com.example.marketplace.dao.BuyerOrderRepo;
import com.example.marketplace.dao.OrderItemRepo;
import com.example.marketplace.dao.ProductRepo;
import com.example.marketplace.dao.SellerOrderRepo;
import com.example.marketplace.model.BuyerOrder;
import com.example.marketplace.model.OrderItem;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.SellerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    BuyerOrderRepo buyerOrderDao;
    @Autowired
    SellerOrderRepo sellerOrderDao;
    @Autowired
    OrderItemRepo orderItemDao;
    public List<BuyerOrder> getBuyerOrders(){
        return buyerOrderDao.findAll();
    }
    public List<BuyerOrder> getBuyerOrdersByBuyerId(Long id){
        return buyerOrderDao.findAllByBuyer_Id(id);
    }
    public List<SellerOrder> getSellerOrdersBySellerId(Long id){
        return sellerOrderDao.findAllBySeller_Id(id);
    }
    public List<SellerOrder> getSellerOrders() {
        return sellerOrderDao.findAll();
    }
    public void reviewOrder(OrderItem orderItem){

        double rating = orderItem.getRating();
        Product product =orderItem.getProduct();
        double newRating=(rating+(product.getRating()* product.getNumberRatings()))/product.getNumberRatings()+1;
        product.setRating(newRating);
        product.setNumberRatings(product.getNumberRatings()+1);
        orderItemDao.save(orderItem);
        productRepo.save(product);
    }
    public void updateBuyerOrder(BuyerOrder buyerOrder){
        buyerOrderDao.save(buyerOrder);
    }
    public void updateSellerOrder(SellerOrder sellerOrder){
        sellerOrderDao.save(sellerOrder);
    }
    public void saveBuyerOrder(BuyerOrder buyerOrder){
        buyerOrderDao.save(buyerOrder);
        for (SellerOrder sellerOrder : buyerOrder.getSellerOrders()) {
            sellerOrder.setBuyerOrder(buyerOrder);
            sellerOrderDao.save(sellerOrder);
            for (OrderItem orderItem:sellerOrder.getOrderItems()) {
                orderItem.setOrder(sellerOrder);
                orderItemDao.save(orderItem);
            }
        }
    }
}
