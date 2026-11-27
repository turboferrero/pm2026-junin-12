package ar.edu.unnoba.pm2026.courses.dto;

import java.math.BigDecimal;
import java.util.Date;

public record CreateCourseRequestDTO(
        String title,
        String description,
        Date startDate,
        Date endDate,
        int maxCapacity,
        BigDecimal basePrice,
        String specialty
        )
    {
}