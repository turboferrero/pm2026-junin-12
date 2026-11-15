package ar.edu.unnoba.pm2026.courses.model;

public class Borrador extends EstadoCurso {

    public Borrador() {
    }

    public Borrador(Curso curso) {
        super(curso);
    }

    @Override
    public void solicitarAprobacion() {
        curso.setEstadoCurso(new PendAprobacion(curso));
    }

    @Override
    public boolean puedeEditarseOEliminarsePorInstructor() {
        return true;
    }
}