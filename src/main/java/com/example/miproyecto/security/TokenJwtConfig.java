package com.example.miproyecto.security;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class TokenJwtConfig {
    // Clave secreta para firmar y verificar tokens JWT
    // La forma correcta de crear una clave secreta sería utilizar un mecanismo más seguro, como la generación aleatoria de bytes.
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String APPLICATION= "/application/json";
}
