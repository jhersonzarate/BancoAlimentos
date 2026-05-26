package com.bancoalimentos.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utilidad para generar y validar JSON Web Tokens (JWT).
 * Usa la librería JJWT 0.12.6 con firma HMAC-SHA256.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; // milisegundos (por defecto 24h = 86400000)

    // ── Genera la clave de firma a partir del secreto configurado ──────────

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ── Genera un JWT con email y rol como claims ──────────────────────────

    public String generarToken(String email, String rol) {
        Date ahora     = new Date();
        Date vencimiento = new Date(ahora.getTime() + expiration);

        return Jwts.builder()
                .subject(email)
                .claim("rol", rol)
                .issuedAt(ahora)
                .expiration(vencimiento)
                .signWith(getSigningKey())
                .compact();
    }

    // ── Extrae el email (subject) del token ────────────────────────────────

    public String extraerEmail(String token) {
        return parsearClaims(token).getSubject();
    }

    // ── Extrae el rol del token ────────────────────────────────────────────

    public String extraerRol(String token) {
        return parsearClaims(token).get("rol", String.class);
    }

    // ── Valida que el token sea auténtico y no haya expirado ───────────────

    public boolean esValido(String token) {
        try {
            Claims claims = parsearClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ── Parsea y verifica la firma del token ──────────────────────────────

    private Claims parsearClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}