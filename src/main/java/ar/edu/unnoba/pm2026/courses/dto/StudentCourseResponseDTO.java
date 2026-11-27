package ar.edu.unnoba.pm2026.courses.dto;

import java.math.BigDecimal;
import java.util.Date;

public record StudentCourseResponseDTO(
        long id,
        String titulo,
        String descripcion,
        Date fechaInicio,
        Date fechaCierre,
        int cupoMaximoAlumnos,
        int cuposDisponibles,
        String especialidad,
        String instructorNombreCompleto,
        BigDecimal precioBase,
        BigDecimal precioAplicable,
        boolean yaInscripto
) {}
