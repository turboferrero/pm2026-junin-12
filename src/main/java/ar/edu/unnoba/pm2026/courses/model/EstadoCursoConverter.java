package ar.edu.unnoba.pm2026.courses.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EstadoCursoConverter implements AttributeConverter<EstadoCurso, String> {

    // De Java a la base: objeto -> texto
    @Override
    public String convertToDatabaseColumn(EstadoCurso estado) {
        if (estado == null) {
            return null;
        }
        if (estado instanceof Borrador) {
            return "BORRADOR";
        }
        if (estado instanceof PendAprobacion) {
            return "PEND_APROBACION";
        }
        if (estado instanceof Publicado) {
            return "PUBLICADO";
        }
        throw new IllegalArgumentException("Estado desconocido: " + estado.getClass().getName());
    }

    // De la base a Java: texto -> objeto
    @Override
    public EstadoCurso convertToEntityAttribute(String valor) {
        if (valor == null) {
            return null;
        }
        switch (valor) {
            case "BORRADOR":
                return new Borrador();
            case "PEND_APROBACION":
                return new PendAprobacion();
            case "PUBLICADO":
                return new Publicado();
            default:
                throw new IllegalArgumentException("Estado desconocido en la base: " + valor);
        }
    }
}