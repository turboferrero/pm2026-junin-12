package ar.edu.unnoba.pm2026.courses.model;

public class Draft extends CourseState {

    public Draft() {
    }

    public Draft(Course course) {
        super(course);
    }

    @Override
    public void solicitarAprobacion() {
        course.setEstadoCurso(new PendingApproval(course));
    }

    @Override
    public boolean puedeEditarseOEliminarsePorInstructor() {
        return true;
    }
}