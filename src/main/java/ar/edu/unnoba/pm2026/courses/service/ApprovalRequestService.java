package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.ApprovalRequestResponseDTO;

import java.util.List;

public interface ApprovalRequestService {
    public List<ApprovalRequestResponseDTO> getPendingRequests();
    public void resolve(Long id, String status, String adminComment) throws Exception;
}
