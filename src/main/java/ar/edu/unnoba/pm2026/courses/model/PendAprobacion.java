package ar.edu.unnoba.pm2026.courses.model;

public class PendAprobacion extends EstadoCurso {

    public PendAprobacion() {
    }

    public PendAprobacion(Curso curso) {
        super(curso);
    }

    @Override
    public void aprobar() {
        curso.setEstadoCurso(new Publicado(curso));
    }

    @Override
    public void rechazar() {
        curso.setEstadoCurso(new Borrador(curso));
    }
}