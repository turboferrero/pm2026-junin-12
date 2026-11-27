package ar.edu.unnoba.pm2026.courses.dto;

import java.math.BigDecimal;
import java.util.Date;

public record InstructorCoursesDetalleResponseDTO(
        Long id,
        String title,
        String description,
        Date startDate,
        Date endDate,
        String status,
        int maxCapacity,
        int enrolledStudents,
        BigDecimal basePrice,
        String specialty
) {
}
