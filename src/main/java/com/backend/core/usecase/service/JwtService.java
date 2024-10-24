package com.backend.core.usecase.service;

import com.backend.core.entity.account.model.Account;
import com.backend.core.infrastructure.business.account.repository.AccountRepository;
import com.backend.core.infrastructure.config.constants.ConstantValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Autowired
    AccountRepository accountRepo;


    public String getUserNameFromJwt(String jwt) {
        return getSingleClaimFromJwt(jwt, Claims::getSubject);
    }


    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        final String userName = userDetails.getUsername();
        final Account account = accountRepo.getAccountByUserName(userName);

        return getUserNameFromJwt(jwt).equals(userName) && !isJwtExpired(jwt) && jwt.equals(account.getCurrentJwt());
    }


    public boolean isJwtExpired(String jwt) {
        String userName = getUserNameFromJwt(jwt);
        Account account = accountRepo.getAccountByUserName(userName);

        return getJwtExpiration(jwt).before(new Date()) && !account.getCurrentJwt().isEmpty();
    }


    public Date getJwtExpiration(String jwt) {
        return getSingleClaimFromJwt(jwt, Claims::getExpiration);
    }


    public String generateJwt(UserDetails userDetails) {
        return generateJwt(new HashMap<>(), userDetails);
    }


    public void expireJwt(String jwt) {
        String userName = getUserNameFromJwt(jwt);
        Account account = accountRepo.getAccountByUserName(userName);
        account.setCurrentJwt("");
        accountRepo.save(account);
    }


    public String getJwtFromRequest(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");

        if (jwtToken == null) {
            return null;
        } else {
            return request.getHeader("Authorization").substring(7);
        }
    }


    public String generateJwt(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }


    public <T> T getSingleClaimFromJwt(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromJwt(jwt);
        return claimsResolver.apply(claims);
    }


    public Claims getAllClaimsFromJwt(String jwt) {
        return Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(jwt)
                .getBody();
    }


    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(ConstantValue.SECRET_SIGNING_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
