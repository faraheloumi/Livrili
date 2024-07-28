package com.example.marketplace.controller;

import com.example.marketplace.exception.BadRequestException;
import com.example.marketplace.exception.ForbiddenException;
import com.example.marketplace.exception.NotFoundException;
import com.example.marketplace.exception.UnAuthorizedException;
import com.example.marketplace.model.Account;
import com.example.marketplace.model.Brand;
import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;
import com.example.marketplace.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/product")
@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AccountService accountService;
    @Autowired
    BrandService brandService;
    @Autowired
    FileStorageService fileStorageService;
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAll();
    }
    @GetMapping("/category/{name}")
    public List<Product> getByCategory(@PathVariable String name){
        return productService.getByCategory(name);
    }
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
        Optional<Product> product= productService.getProduct(id);
        if (product.isPresent()){
            return product.get();
        }else {
            throw new NotFoundException();
        }
    }
    @PostMapping("/brand")
    public List<Product> getByBrands(@RequestBody List<Brand> brands){
        return productService.getProductByBrands(brands);
    }@GetMapping("/price/{min}/{max}")
    public List<Product> getByPriceRange(@PathVariable double min,@PathVariable double max){
        return productService.getProductByPriceBetween(min,max);
    }


    @GetMapping("/search/{query}")
    public List<Product> search(@PathVariable String query){
        return productService.search(query);
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<?> getProductImage(@PathVariable String imageName) throws IOException {
        byte[] image= fileStorageService.loadAsResource("product/"+imageName).getContentAsByteArray();
        MediaType mediaType = MediaTypeFactory.getMediaType(imageName).orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(image);
    }
    @GetMapping("/test")
    public void test(){
        brandService.save(new Brand(1L,"brand","test",new ArrayList<>()));
        categoryService.save(new Category(1L,"category","test",new ArrayList<>()));
        Account account=new Account();
        account.setUsername("test");
        account.setEmail("test");
        accountService.save(account);
    }
    @PostMapping
    public void saveProduct(@RequestParam("name") String name,
                            @RequestParam("description") String description,
                            @RequestParam("price") double price,
                            @RequestParam("quantity") int quantity,
                            @RequestParam("categoryId") Long categoryId,
                            @RequestParam("brandId") Long brandId,
                            @RequestParam(value = "image") MultipartFile image,
                            Authentication authentication) {

        Jwt jwt=(Jwt) authentication.getPrincipal();
        Long userId = (Long) jwt.getClaims().get("id");

        Optional<Category> category=categoryService.getById(categoryId);

        Optional<Account> account= accountService.getById(userId);

        Optional<Brand> brand= brandService.getById(brandId);

        if(category.isPresent()&&account.isPresent()&& brand.isPresent()){
            String imageName=new Date().getTime()+image.getOriginalFilename();
            Product product=new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setImage(imageName);
            product.setBrand(brand.get());
            product.setCategory(category.get());
            product.setSeller(account.get());
            product.setQuantity(quantity);
            productService.save(product);
            fileStorageService.store(image,"product/"+imageName);
        }else{
            throw new BadRequestException();
        }
    }
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id,
                              @RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("price") double price,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("categoryId") Long categoryId,
                              @RequestParam("brandId") Long brandId,
                              @RequestParam(value="image",required=false) MultipartFile image,
                              Authentication authentication) throws IOException {
        Jwt jwt=(Jwt) authentication.getPrincipal();
        Long userId = (Long) jwt.getClaims().get("id");
        Optional<Product> oldProduct=productService.getProduct(id);
        if(oldProduct.isEmpty()){
            throw new BadRequestException();
        }else {
            Optional<Category> category=categoryService.getById(categoryId);

            Optional<Account> account= accountService.getById(userId);

            Optional<Brand> brand= brandService.getById(brandId);

            if(category.isPresent()&&account.isPresent()&& brand.isPresent()){
                Product product=oldProduct.get();

                if (product.getSeller().getId() != userId) {
                    throw new ForbiddenException();
                } else {
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(price);
                    product.setBrand(brand.get());
                    product.setCategory(category.get());
                    product.setSeller(account.get());
                    product.setQuantity(quantity);
                    if (image!=null){
                        String imageName=new Date().getTime()+image.getOriginalFilename();
                        String productImage = product.getImage();
                        product.setImage(imageName);
                        fileStorageService.delete("product/" + productImage);
                        fileStorageService.store(image,"product/"+imageName);
                    }
                    productService.save(product);
                }
            }else{
                throw new BadRequestException();
            }
        }
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id, Authentication authentication) throws IOException {
        Jwt jwt=(Jwt) authentication.getPrincipal();
        Long userId = (Long) jwt.getClaims().get("id");
        Optional<Product> product=productService.getProduct(id);
        if(product.isEmpty()){
            throw new BadRequestException();
        }else {
            if (product.get().getSeller().getId() != userId) {
                throw new UnAuthorizedException();
            } else {
                String productImage = product.get().getImage();
                productService.deleteProduct(id);
                fileStorageService.delete("product/" + productImage);
            }
        }
    }
}
