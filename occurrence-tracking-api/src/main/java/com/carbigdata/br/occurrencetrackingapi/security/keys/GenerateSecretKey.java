package com.carbigdata.br.occurrencetrackingapi.security.keys;

import io.jsonwebtoken.Jwts;

import java.util.Base64;

public class GenerateSecretKey {
    public static void main(String[] args) {
        String secretKey = Base64.getEncoder().encodeToString(Jwts.SIG.HS256.key().build().getEncoded());
        System.out.println("Generated Key: " + secretKey);
    }
}
