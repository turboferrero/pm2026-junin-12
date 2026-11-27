package ar.edu.unnoba.pm2026.courses.dto;

public record CreateStudentRequestDTO(
        String name,
        String lastName,
        String dni,
        String email,
        String password
) {}
