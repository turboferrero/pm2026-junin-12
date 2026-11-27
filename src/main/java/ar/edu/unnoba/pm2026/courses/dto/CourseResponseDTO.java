package ar.edu.unnoba.pm2026.courses.dto;

import ar.edu.unnoba.pm2026.courses.model.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record CourseResponseDTO(
        Long id,
        String title,
        String description,
        Date startDate,
        String instructorEmail
) {
    public static CourseResponseDTO from(Course course) {
        return new CourseResponseDTO(
                course.getId(),
                course.getTitulo(),
                course.getDescripcion(),
                course.getFechaInicio(),
                course.getEmailInstructor()
        );
    }
}
