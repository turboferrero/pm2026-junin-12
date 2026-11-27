package ar.edu.unnoba.pm2026.courses.dto;

import ar.edu.unnoba.pm2026.courses.model.Enrollment;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record EnrolledStudentResponseDTO(
        Long studentId,
        String name,
        String lastName,
        String email,
        Date enrollmentDate,
        BigDecimal finalPrice
) {
    public static EnrolledStudentResponseDTO from(Enrollment inscripcion) {
        return new EnrolledStudentResponseDTO(
                inscripcion.getEstudianteId(),
                inscripcion.getEstudianteNombre(),
                inscripcion.getEstudianteApellido(),
                inscripcion.getEstudianteEmail(),
                inscripcion.getFechaInscripcion(),
                inscripcion.getPrecioFinal()
        );
    }
}