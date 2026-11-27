package ar.edu.unnoba.pm2026.courses.repository;
import ar.edu.unnoba.pm2026.courses.model.ApprovalRequest;
import ar.edu.unnoba.pm2026.courses.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long>{
    public List<ApprovalRequest> findByState(State state);
}
