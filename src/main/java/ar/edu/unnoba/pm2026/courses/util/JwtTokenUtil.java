package ar.edu.unnoba.pm2026.courses.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class JwtTokenUtil {
    private final Algorithm algorithm = Algorithm.HMAC512("tu_clave_secreta");

    public String generateToken(String email) {
        String token = JWT.create().withSubject(email).withExpiresAt(Instant.now().plus(10, ChronoUnit.DAYS)).sign(algorithm);
        return "Bearer " + token;
    }

    public String getSubject(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try{
            return JWT.require(algorithm).build().verify(token).getSubject();
        }
        catch (JWTVerificationException e) {
            return null;
        }
    }
    public boolean verify(String token) {
        return getSubject(token)!=null;
    }
}
