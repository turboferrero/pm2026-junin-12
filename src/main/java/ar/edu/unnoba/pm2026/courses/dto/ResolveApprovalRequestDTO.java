package ar.edu.unnoba.pm2026.courses.dto;

import ar.edu.unnoba.pm2026.courses.model.ApprovalRequest;

public record ResolveApprovalRequestDTO(String status, String adminComment) {
    public static ResolveApprovalRequestDTO from(ApprovalRequest request) {
        return new ResolveApprovalRequestDTO(
                request.getStateName(),
                request.getComentarioAdmin()
        );
    }
}
