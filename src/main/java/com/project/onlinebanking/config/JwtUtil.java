package com.project.onlinebanking.config;
import com.project.onlinebanking.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String secret = generateSecretKey();
    private static final long expiration_time = 864000000; // 10 days

    private static String generateSecretKey() {
        byte[] secretKeyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        return Base64.getEncoder().encodeToString(secretKeyBytes);
    }

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration_time))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getNameFromJwt(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(secret).build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // Перевірка, чи токен дійсний
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getNameFromJwt(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Перевірка, чи закінчився токен
    private boolean isTokenExpired(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(secret).build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }
}
