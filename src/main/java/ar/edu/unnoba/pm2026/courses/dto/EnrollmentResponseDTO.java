package ar.edu.unnoba.pm2026.courses.dto;

import java.math.BigDecimal;
import java.util.Date;

public record EnrollmentResponseDTO(
        long id,
        long courseId,
        String courseTitulo,
        Date fechaInscripcion,
        BigDecimal precioBase,
        BigDecimal descuentoAplicado,
        BigDecimal precioFinal
) {}
