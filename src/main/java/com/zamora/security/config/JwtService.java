package com.zamora.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "88a0a7569cd65913e034dd811f2007b595d98c97b9d8b02debe8e7fd466c0aa9";



    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    //Genera token tan solo requiriendo como parametro un objeto de tipo UserDetails
    public String generateToken(UserDetails userDetails){

        return generateToken(new HashMap<>(), userDetails);
    }

    //Genera token requiriendo claims adicionales
    public String generateToken(
        Map<String, Object> extraClaim,
        UserDetails userDetails){

        return Jwts
               .builder()
               .setClaims(extraClaim)
               .setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
               .signWith(getSignIngKey(), SignatureAlgorithm.HS256)
               .compact();
     }



     public boolean isTokenExpired(String token){

        return extractExpiration(token).before(new Date());
     }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }



    private Claims extractAllClaims(String token){
         return Jwts
                .parserBuilder()
                .setSigningKey(getSignIngKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignIngKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
         return Keys.hmacShaKeyFor(keyBytes);
    }


}
