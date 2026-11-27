package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.ApprovalRequestResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.ApprovalRequest;
import ar.edu.unnoba.pm2026.courses.model.State;
import ar.edu.unnoba.pm2026.courses.repository.ApprovalRequestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ApprovalRequestServiceImp implements  ApprovalRequestService {
    private ApprovalRequestRepository approvalRequestRepository;
    public ApprovalRequestServiceImp(ApprovalRequestRepository approvalRequestRepository) {
        this.approvalRequestRepository = approvalRequestRepository;
    }
    @Override
    public List<ApprovalRequestResponseDTO> getPendingRequests() {
        List<ApprovalRequest> pendientes = approvalRequestRepository.findByState(State.SUBMITTED);
        List<ApprovalRequestResponseDTO> response = new ArrayList<>();

        for (ApprovalRequest request : pendientes) {
            response.add(ApprovalRequestResponseDTO.from(request));
        }

        return response;
    }
    @Override
    public void resolve(Long id, String status, String adminComment) throws Exception {
        ApprovalRequest solicitud = approvalRequestRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada"));

        solicitud.resolve(status, adminComment);
        approvalRequestRepository.save(solicitud);
    }
}
