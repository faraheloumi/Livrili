package com.example.marketplace.service;

import com.example.marketplace.model.Account;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class TokenService {
    private final JwtEncoder encoder;
    public TokenService(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
    }
    public String generateToken(Account account) {
        Consumer<Map<String, Object>> claimsModifier = claimMap -> {
            claimMap.put("id", account.getId());
            claimMap.put("modifiedon", account.getModifiedOn().getTime());
        };
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(3, ChronoUnit.HOURS))
                .subject(account.getUsername())
                .claims((Consumer<Map<String, Object>>) claimsModifier)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    public Account getTokenAccount(Jwt token){
        Account account=new Account();
        account.setId((Long) token.getClaims().get("id"));
        return account;
    }

}