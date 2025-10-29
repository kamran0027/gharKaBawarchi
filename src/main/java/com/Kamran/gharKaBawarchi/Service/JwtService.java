package com.Kamran.gharKaBawarchi.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {
    private static final String secreteKey="c8a91f1b5d2c4e97a64b8e13a77d93fbc8a91f1b5d2c4e97a64b8e13a77d93fb";

    private Key signinKey;

    private long expirationMillis=1000*60*60;

    @PostConstruct
    public void init(){
        signinKey=Keys.hmacShaKeyFor(secreteKey.getBytes());
    }

    public String generateToken(String email, String role){
        //Map<String,Object> claims=new HashMap<>();
        //claims.put("role", role);

        return Jwts.builder()
                .claim("role",role)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(signinKey,SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseClaims(String token) throws JwtException{
        return Jwts.parserBuilder()
                .setSigningKey(signinKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token) throws JwtException{
        return extractClaim(token,Claims::getSubject);
    }

    public String extractRoles(String token){
        Claims c=parseClaims(token);
        Object r=c.get("role");
        return r==null?null:r.toString();

    }
    public <T> T extractClaim(String token, Function<Claims, T> resolver) throws JwtException {
        Claims claims = parseClaims(token);
        return resolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        Date exp = extractClaim(token, Claims::getExpiration);
        return exp.before(new Date());
    }

    public long getExpirationMillis() {
        return expirationMillis;
    }
}
