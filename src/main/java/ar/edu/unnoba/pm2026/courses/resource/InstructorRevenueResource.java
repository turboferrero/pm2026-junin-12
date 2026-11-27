package ar.edu.unnoba.pm2026.courses.resource;

import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.service.AuthorizationService;
import ar.edu.unnoba.pm2026.courses.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/instructors")
public class InstructorRevenueResource {

    private final AuthorizationService authorizationService;
    private final CursoService cursoService;

    public InstructorRevenueResource(
            AuthorizationService authorizationService,
            CursoService cursoService) {
        this.authorizationService = authorizationService;
        this.cursoService = cursoService;
    }

    @GetMapping("/me/revenue")
    public ResponseEntity<?> getRevenue(
            @RequestHeader("Authorization") String token) {

        Instructor instructor;

        try {
            instructor = authorizationService.authorize(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }

        try {
            BigDecimal revenue =
                    cursoService.getInstructorRevenue(instructor.getId());

            return ResponseEntity.ok(revenue);

        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }
    }
}