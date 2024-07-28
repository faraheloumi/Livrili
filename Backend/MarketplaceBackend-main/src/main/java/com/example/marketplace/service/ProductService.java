package com.example.marketplace.service;

import com.example.marketplace.dao.ProductRepo;
import com.example.marketplace.model.Brand;
import com.example.marketplace.model.Product;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepo productDao;
    public List<Product> getAll(){
        return productDao.findAll();
    }

    public void save(Product product){
        productDao.save(product);
    }
    public Optional<Product> getProduct(Long id){
        return productDao.findById(id);
    }
    public List<Product> getByCategory(String name){
        return productDao.findProductsByCategory_Name(name);
    }
    public List<Product> search(String query){
        List<Product> products=productDao.findAll();
        return searchListBySimilarity(query,products);
    }
    private List<Product> searchListBySimilarity(String query,List<Product> products){
        List<Product> filteredProducts=products.stream()
                .filter(product -> calculateProductSimilarity(query,product) >= 1)
                .collect(Collectors.toList());
        filteredProducts.sort(Comparator.comparingDouble(product -> calculateProductSimilarity(query, product)));

        return filteredProducts;
    }
    private double calculateProductSimilarity(String s1,Product product){
        return calculateSimilarity(s1,product.getName())*2.5+
                calculateSimilarity(s1,product.getDescription())*1.5+
                calculateSimilarity(s1,product.getBrand().getName())*1.5+
                calculateSimilarity(s1,product.getCategory().getName())*1.5+
                product.getRating()*0.5;
    }
    public List<Product> getProductByBrands(List<Brand> brands){
        return productDao.findProductsByBrandIn(brands);
    }
    public List<Product> getProductByPriceBetween(double minPrice, double maxPrice){
        return productDao.findByPriceBetween(minPrice, maxPrice);
    }


    private double calculateSimilarity(String s1,String s2){
        LevenshteinDistance distance = new LevenshteinDistance();
        return 1-(double) distance.apply(s1,s2)/Math.max(s1.length(),s2.length());
    }


    public void deleteProduct(Long id){
         productDao.deleteById(id);
    }

}
