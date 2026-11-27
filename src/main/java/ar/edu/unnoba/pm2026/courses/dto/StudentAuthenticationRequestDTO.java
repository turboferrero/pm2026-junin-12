package ar.edu.unnoba.pm2026.courses.dto;

public record StudentAuthenticationRequestDTO(
        String email,
        String password
) {}