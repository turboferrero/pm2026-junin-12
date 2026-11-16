package ar.edu.unnoba.pm2026.courses.model;

public class PendingApproval extends CourseState {

    public PendingApproval() {
    }

    public PendingApproval(Course course) {
        super(course);
    }

    @Override
    public void aprobar() {
        course.setEstadoCurso(new Published(course));
    }

    @Override
    public void rechazar() {
        course.setEstadoCurso(new Draft(course));
    }
}