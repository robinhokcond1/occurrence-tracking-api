package com.carbigdata.br.occurrencetrackingapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final MacAlgorithm ALGORITHM = Jwts.SIG.HS256; // Novo formato da JJWT 0.12.6
    private static final String SECRET_KEY = "SeuSegredoMuitoGrandeParaTerNoMinimo256Bits"; // Gere uma chave segura

    private final SecretKey key;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)); // Certifique-se que a chave tem no mínimo 256 bits
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token expira em 1 hora
                .signWith(key, ALGORITHM)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
