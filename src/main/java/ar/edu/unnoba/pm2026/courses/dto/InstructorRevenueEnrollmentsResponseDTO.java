package ar.edu.unnoba.pm2026.courses.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record InstructorRevenueEnrollmentsResponseDTO(
        BigDecimal totalRevenue,
        List<Map<String, Object>> enrollments
) {
}