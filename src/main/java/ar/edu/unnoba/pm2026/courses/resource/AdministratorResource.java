package ar.edu.unnoba.pm2026.courses.resource;

import ar.edu.unnoba.pm2026.courses.dto.*;
import ar.edu.unnoba.pm2026.courses.model.Administrator;
import ar.edu.unnoba.pm2026.courses.service.AdministratorService;
import ar.edu.unnoba.pm2026.courses.service.ApprovalRequestService;
import ar.edu.unnoba.pm2026.courses.service.AuthenticathionService;
import ar.edu.unnoba.pm2026.courses.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
public class AdministratorResource {
    private final AdministratorService administratorService;
    private final AuthorizationService authorizationService;
    private final AuthenticathionService authenticathionService;
    private final ApprovalRequestService approvalRequestService;

    public AdministratorResource(AdministratorService administratorService, AuthorizationService authorizationService, AuthenticathionService authenticathionService, ApprovalRequestService approvalRequestService) {
        this.administratorService = administratorService;
        this.authorizationService = authorizationService;
        this.authenticathionService = authenticathionService;
        this.approvalRequestService = approvalRequestService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> create(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody CreateAdminRequestDTO dto) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            authorizationService.authorizeAdmin(token);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (dto.email() == null || dto.email().isBlank() ||
                dto.password() == null || dto.password().isBlank() ||
                dto.name() == null || dto.name().isBlank() ||
                dto.lastname() == null || dto.lastname().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Administrator administrator = new Administrator();
        administrator.setEmail(dto.email());
        administrator.setContrasenia(dto.password());
        administrator.setNombre(dto.name());
        administrator.setApellido(dto.lastname());
        try {
            administratorService.create(administrator);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(value = "/auth", produces = "application/json")
    public ResponseEntity<?> adminAuthentication(@RequestBody AdminAuthenticationRequestDTO dto) {
        Administrator administrator = new Administrator();
        administrator.setEmail(dto.email());
        administrator.setContrasenia(dto.password());

        try {
            String token = authenticathionService.authenticateAdmin(administrator);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value="/accounts", produces = "application/json")
    public ResponseEntity<?> getAdmins(@RequestHeader(value = "Authorization", required = false) String token){
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try{
            authorizationService.authorizeAdmin(token);

        }
        catch (SecurityException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
        List<AdminResponseDTO> admins = administratorService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(admins);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAdmin(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("id") Long idadmin){
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Administrator authenticatedAdmin;
        try{
            authenticatedAdmin = authorizationService.authorizeAdmin(token);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try{
            administratorService.delete(idadmin,authenticatedAdmin.getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/approval-requests")
    public ResponseEntity<?> getApprovalRequests(@RequestHeader(value = "Authorization", required = false) String token){
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            authorizationService.authorizeAdmin(token);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<ApprovalRequestResponseDTO> pendingRequests = approvalRequestService.getPendingRequests();
        return ResponseEntity.status(HttpStatus.OK).body(pendingRequests);
    }
    @PutMapping("/approval-requests/{id}")
    public ResponseEntity<?> resolveApprovalRequest(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("id") Long id, @RequestBody(required = false) ResolveApprovalRequestDTO dto) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Administrator authenticatedAdmin;
        try {
            authenticatedAdmin = authorizationService.authorizeAdmin(token);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (dto == null || dto.status() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try{
            approvalRequestService.resolve(id, dto.status(), dto.adminComment());
            return ResponseEntity.status(HttpStatus.OK).build();
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (IllegalStateException | IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }

}
