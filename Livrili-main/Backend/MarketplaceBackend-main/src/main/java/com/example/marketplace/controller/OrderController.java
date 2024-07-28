package com.example.marketplace.controller;

import com.example.marketplace.model.*;
import com.example.marketplace.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/order")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AccountService accountService;
    @Autowired
    BrandService brandService;
    @GetMapping
    public List<BuyerOrder> getBuyerOrders(){
        return orderService.getBuyerOrders();
    }

    // not tested
    @GetMapping("/buyer/{id}")
    public List<BuyerOrder> getBuyerOrders(@PathVariable Long id){
        return orderService.getBuyerOrdersByBuyerId(id);
    }
    // not tested
    @GetMapping("/seller/{id}")
    public List<SellerOrder> getSellerOrders(@PathVariable Long id){
        return orderService.getSellerOrdersBySellerId(id);
    }
    //send
    @PostMapping
    public void saveOrder(@RequestBody BuyerOrder buyerOrder, Authentication authentication){
        Jwt jwt=(Jwt) authentication.getPrincipal();
        Long buyerId = (Long) jwt.getClaims().get("id");
        Account account=new Account();
        account.setId(buyerId);
        buyerOrder.setBuyer(account);
        orderService.saveBuyerOrder(buyerOrder);
    }

    @PostMapping("/review")
    public void reviewOrder(@RequestBody OrderItem orderItem){
        orderService.reviewOrder(orderItem);
    }
    @PutMapping("/buyer")
    public void updateBuyerOrder(@RequestBody BuyerOrder buyerOrder){
        orderService.updateBuyerOrder(buyerOrder);
    }
    @PutMapping("/seller")
    public void updateSellerOrder(@RequestBody SellerOrder sellerOrder){
        orderService.updateSellerOrder(sellerOrder);
    }

    @GetMapping("/test")
    public void test(){
        Brand brand =new Brand();
        brand.setName("brand");
        brandService.save(brand);
        Category category =new Category();
        category.setName("category");
        categoryService.save(new Category(1L,"category","test",new ArrayList<>()));
        Account seller=new Account();
        seller.setUsername("buyer");
        seller.setPassword("test");
        Account buyer=new Account();
        buyer.setUsername("seller");
        buyer.setPassword("test");
        accountService.save(seller);
        accountService.save(buyer);
        Brand foundBrand=brandService.getById(1L).orElse(null);
        Category foundCategory=categoryService.getById(1L).orElse(null);
        Account foundSeller=accountService.getById(1L).orElse(null);
        Product product=new Product();
        product.setName("test");
        product.setBrand(foundBrand);
        product.setCategory(foundCategory);
        product.setSeller(foundSeller);
        productService.save(product);
    }
}