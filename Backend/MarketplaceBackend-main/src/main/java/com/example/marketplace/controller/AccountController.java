package com.example.marketplace.controller;

import com.example.marketplace.config.StorageProperties;
import com.example.marketplace.exception.ForbiddenException;
import com.example.marketplace.exception.UnAuthorizedException;
import com.example.marketplace.model.Account;
import com.example.marketplace.service.AccountService;
import com.example.marketplace.service.FileStorageService;
import com.example.marketplace.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/api/v1/account")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    TokenService tokenService;
    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody Account account) {
        Account found=accountService.getByUsername(account.getUsername(),account.getPassword());
        if(found!=null){
            String token = tokenService.generateToken(found);
            return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
        }else {
            throw new UnAuthorizedException();
        }
    }

    @PostMapping("/signup")
    public void saveAccount(@RequestBody Account account) {
        accountService.save(account);
    }

    @PostMapping("/test")
    public String test() {
        return "access";
    }
    @GetMapping("/image/{imageName}")
    public ResponseEntity<?> getProductImage(@PathVariable String imageName) throws IOException {
        byte[] image= fileStorageService.loadAsResource("account/"+imageName).getContentAsByteArray();
        MediaType mediaType = MediaTypeFactory.getMediaType(imageName).orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(image);
    }
    /*
    @PostMapping("/login/username")
    public Account loginByUsername(@RequestBody Account account){
        return accountService.getByUsername(account.getUsername(),account.getPassword());
    }
    @PostMapping("/login/email")
    public Account loginByEmail(@RequestBody Account account){
        return accountService.getByEmail(account.getEmail(),account.getPassword());
    }
    */

}
