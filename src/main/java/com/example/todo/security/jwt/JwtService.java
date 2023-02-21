package com.example.todo.security.jwt;

import com.example.todo.domain.models.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.util.Date.from;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signKey}")
    private String signKey;

    public String generateToken(Account account) {
        var exp = Long.parseLong(this.expiration);
        var dateTimeExpiration = LocalDateTime.now().plusMinutes(exp);
        var instant = dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant();
        var date = from(instant);

        return Jwts
                .builder()
                .setSubject(account.getEmail())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, this.signKey)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(this.signKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidToken(String token) {
        try {
            var claims = getClaims(token);
            var expirationDate = claims.getExpiration();
            var date = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return LocalDateTime.now().isBefore(date);
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoggedAccount(String token) {
        return getClaims(token).getSubject();
    }

}
