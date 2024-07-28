package com.example.marketplace.service;

import com.example.marketplace.dao.CategoryRepo;
import com.example.marketplace.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryDao;
    public void save(Category category){
        categoryDao.save(category);
    }
    public List<Category> getAll(){
        return categoryDao.findAll();
    }

    public Optional<Category> getById(Long id){
        return categoryDao.findById(id);
    }

}
