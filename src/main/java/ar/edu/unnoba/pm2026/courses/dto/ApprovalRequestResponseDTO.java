package ar.edu.unnoba.pm2026.courses.dto;

import java.util.Date;

public record ApprovalRequestResponseDTO(
        Long id,
        Long courseId,
        String courseTitle,
        Date requestDate,
        String instructorComment,
        String status
) {
}