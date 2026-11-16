package ar.edu.unnoba.pm2026.courses.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CourseStateConverter implements AttributeConverter<CourseState, String> {

    // De Java a la base: objeto -> texto
    @Override
    public String convertToDatabaseColumn(CourseState estado) {
        if (estado == null) {
            return null;
        }
        if (estado instanceof Draft) {
            return "BORRADOR";
        }
        if (estado instanceof PendingApproval) {
            return "PEND_APROBACION";
        }
        if (estado instanceof Published) {
            return "PUBLICADO";
        }
        throw new IllegalArgumentException("Estado desconocido: " + estado.getClass().getName());
    }

    // De la base a Java: texto -> objeto
    @Override
    public CourseState convertToEntityAttribute(String valor) {
        if (valor == null) {
            return null;
        }
        switch (valor) {
            case "BORRADOR":
                return new Draft();
            case "PEND_APROBACION":
                return new PendingApproval();
            case "PUBLICADO":
                return new Published();
            default:
                throw new IllegalArgumentException("Estado desconocido en la base: " + valor);
        }
    }
}