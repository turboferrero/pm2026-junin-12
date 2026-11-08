package ar.edu.unnoba.pm2026.courses.model;

public abstract class EstadoCurso {
    private Curso curso;
    public EstadoCurso() {
    }

    public EstadoCurso(Curso curso) {
        this.curso = curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
