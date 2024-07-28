package com.example.marketplace.dao;

import com.example.marketplace.model.Brand;
import com.example.marketplace.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<Brand,Long> {
}
