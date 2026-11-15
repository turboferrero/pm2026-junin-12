package ar.edu.unnoba.pm2026.courses.model;

public abstract class EstadoCurso {
    protected Curso curso;

    public EstadoCurso() {
    }

    public EstadoCurso(Curso curso) {
        this.curso = curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void solicitarAprobacion() {
        throw new IllegalStateException("No se puede solicitar aprobación en el estado actual del curso.");
    }

    public void aprobar() {
        throw new IllegalStateException("No se puede aprobar el curso en su estado actual.");
    }

    public void rechazar() {
        throw new IllegalStateException("No se puede rechazar el curso en su estado actual.");
    }

    public boolean puedeEditarseOEliminarsePorInstructor() {
        return false;
    }

    public boolean permiteInscripciones() {
        return false;
    }
}