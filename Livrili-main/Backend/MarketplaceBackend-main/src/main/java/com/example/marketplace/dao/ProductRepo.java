package com.example.marketplace.dao;

import com.example.marketplace.model.Brand;
import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findProductsByCategory_Name(String name);
    List<Product> findByNameContainingIgnoreCase(String query);
    List<Product> findByBrand_NameContainingIgnoreCase(String query);

    List<Product> findByCategory_NameContainingIgnoreCase(String query);
    List<Product> findProductsByBrandIn(List<Brand> brands);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

}
