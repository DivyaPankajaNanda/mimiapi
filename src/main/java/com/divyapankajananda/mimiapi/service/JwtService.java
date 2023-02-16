package com.divyapankajananda.mimiapi.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${MIMI_AUTH_SECRET_KEY}")
    private String MIMI_AUTH_SECRET_KEY;

    @Value("${MIMI_TOKEN_EXPIRATION_DURATION}")
    private int MIMI_TOKEN_EXPIRATION_DURATION;

    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+MIMI_TOKEN_EXPIRATION_DURATION))
                    .signWith(getSigninKey(),SignatureAlgorithm.HS256)
                    .compact();
                    
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(MIMI_AUTH_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token,UserDetails userDetails){
        return (extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
