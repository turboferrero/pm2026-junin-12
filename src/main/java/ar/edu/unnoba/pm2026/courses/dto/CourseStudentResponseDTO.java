package ar.edu.unnoba.pm2026.courses.dto;

import java.math.BigDecimal;
import java.util.Date;

public record CourseStudentResponseDTO(
        Long studentId,
        String name,
        String surname,
        String dni,
        String email,
        Date enrollmentDate,
        BigDecimal finalPrice
) {
}