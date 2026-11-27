package ar.edu.unnoba.pm2026.courses.repository;

import ar.edu.unnoba.pm2026.courses.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {
    public Instructor findByEmail(@Param("email") String
                                          email);
}
