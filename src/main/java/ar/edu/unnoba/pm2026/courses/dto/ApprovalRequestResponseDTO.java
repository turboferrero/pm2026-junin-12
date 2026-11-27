package ar.edu.unnoba.pm2026.courses.dto;
import ar.edu.unnoba.pm2026.courses.model.ApprovalRequest;

import java.util.Date;

public record ApprovalRequestResponseDTO(Long id, Long courseId, String courseTitle, String instructorEmail, String estado, Date fechaSolicitud) {
    public static ApprovalRequestResponseDTO from(ApprovalRequest request) {
        return new ApprovalRequestResponseDTO(
                request.getId(),
                request.getCursoId(),
                request.getCursoTitulo(),
                request.getInstructorEmail(),
                request.getStateName(),
                request.getFechaSolicitud()
        );
    }
}
