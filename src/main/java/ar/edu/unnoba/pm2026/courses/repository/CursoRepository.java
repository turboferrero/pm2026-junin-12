package ar.edu.unnoba.pm2026.courses.repository;
import ar.edu.unnoba.pm2026.courses.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long>{
    public List<Curso> findByCreador_Id(Long instructorId);
}
