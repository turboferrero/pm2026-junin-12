package ar.edu.unnoba.pm2026.courses.repository;

import ar.edu.unnoba.pm2026.courses.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository {
    public List<Enrollment> findByCursoId(Long cursoId);
}
