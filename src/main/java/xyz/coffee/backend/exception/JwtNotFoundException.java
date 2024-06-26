package xyz.coffee.backend.exception;

import io.jsonwebtoken.JwtException;

public class JwtNotFoundException extends JwtException {
    public JwtNotFoundException(String message) {
        super(message);
    }
}
