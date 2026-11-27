package ar.edu.unnoba.pm2026.courses.repository;

import ar.edu.unnoba.pm2026.courses.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository {
    public List<Enrollment> findByCursoId(Long cursoId);
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentIdOrderByFechaInscripcionDesc(long studentId);
    boolean existsByStudentIdAndCourseId(long studentId, long courseId);
    List<Enrollment> findByStudentId(long studentId);
}
