package ar.edu.unnoba.pm2026.courses.model;

public abstract class CourseState {
    protected Course course;

    public CourseState() {
    }

    public CourseState(Course course) {
        this.course = course;
    }

    public void setCurso(Course course) {
        this.course = course;
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