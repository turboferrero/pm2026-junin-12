package ar.edu.unnoba.pm2026.courses.model;

public class Published extends CourseState {

    public Published() {
    }

    public Published(Course course) {
        super(course);
    }

    @Override
    public boolean permiteInscripciones() {
        return true;
    }
}