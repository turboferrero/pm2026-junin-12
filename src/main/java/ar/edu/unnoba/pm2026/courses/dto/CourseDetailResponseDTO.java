package ar.edu.unnoba.pm2026.courses.dto;

import ar.edu.unnoba.pm2026.courses.model.Course;
import java.math.BigDecimal;
import java.util.Date;

public record CourseDetailResponseDTO(
        Long id,
        String title,
        String description,
        String specialty,
        Date startDate,
        Date endDate,
        BigDecimal price,
        String state,
        int enrolledCount,
        int capacity,
        Long instructorId,
        String instructorName,
        String instructorEmail
) {
    public static CourseDetailResponseDTO from(Course course) {
        return new CourseDetailResponseDTO(
                course.getId(),
                course.getTitulo(),
                course.getDescripcion(),
                course.getEspecialidadNombre(),
                course.getFechaInicio(),
                course.getFechaCierre(),
                course.getPrecioBase(),
                course.getEstadoNombre(),
                course.getCantidadInscriptos(),
                course.getCupoMaximoAlumnos(),
                course.getInstructorId(),
                course.getInstructorNombreCompleto(),
                course.getEmailInstructor()
        );
    }
}
