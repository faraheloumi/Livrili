package com.example.marketplace.controller;

import com.example.marketplace.model.Brand;
import com.example.marketplace.model.Category;
import com.example.marketplace.service.BrandService;
import com.example.marketplace.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/brand")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BrandController {
    @Autowired
    BrandService brandService;
    @GetMapping
    public List<Brand> getByCategory(){
        return brandService.getAll();
    }
    @PostMapping
    public void saveCategory(@RequestBody Brand brand){
        brandService.save(brand);
    }
}
