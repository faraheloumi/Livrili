package com.example.marketplace.controller;

import com.example.marketplace.model.Category;
import com.example.marketplace.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/category")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping
    public List<Category> getByCategory(){
        return categoryService.getAll();
    }
    @PostMapping
    public void saveCategory(@RequestBody Category category){
        categoryService.save(category);
    }
}
