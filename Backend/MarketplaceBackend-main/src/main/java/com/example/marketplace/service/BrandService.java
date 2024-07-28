package com.example.marketplace.service;

import com.example.marketplace.dao.BrandRepo;
import com.example.marketplace.dao.CategoryRepo;
import com.example.marketplace.model.Brand;
import com.example.marketplace.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    BrandRepo brandDao;
    public void save(Brand brand){
        brandDao.save(brand);
    }
    public List<Brand> getAll(){
        return brandDao.findAll();
    }

    public Optional<Brand> getById(Long id){
        return brandDao.findById(id);
    }

}
