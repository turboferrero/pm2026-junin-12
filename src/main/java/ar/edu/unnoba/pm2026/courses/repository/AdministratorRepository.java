package ar.edu.unnoba.pm2026.courses.repository;
import ar.edu.unnoba.pm2026.courses.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long>{
}
