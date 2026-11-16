package ar.edu.unnoba.pm2026.courses.repository;
import ar.edu.unnoba.pm2026.courses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long>{
    public List<Course> findByCreadorId(Long creadorId);
}
