package ar.edu.unnoba.pm2026.courses.resource;

import ar.edu.unnoba.pm2026.courses.dto.CreateInstructorRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorAuthenticationRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.service.AuthenticathionService;
import ar.edu.unnoba.pm2026.courses.service.AuthorizationService;
import ar.edu.unnoba.pm2026.courses.service.CourseService;
import ar.edu.unnoba.pm2026.courses.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instructor")
public class InstructorResource {

    private final InstructorService instructorService;
    private final AuthenticathionService authenticathionService;
    private final AuthorizationService authorizationService;
    private final CourseService courseService;

    public InstructorResource(InstructorService instructorService,
                              AuthenticathionService authenticathionService,
                              AuthorizationService authorizationService,
                              CourseService courseService) {
        this.instructorService = instructorService;
        this.authenticathionService = authenticathionService;
        this.authorizationService = authorizationService;
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateInstructorRequestDTO dto) {
        Instructor instructor = new Instructor();
        instructor.setEmail(dto.email());
        instructor.setContrasenia(dto.password());

        try {
            instructorService.create(instructor);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(value = "/auth", produces = "application/json")
    public ResponseEntity<?> authentication(@RequestBody InstructorAuthenticationRequestDTO dto) {
        Instructor instructor = new Instructor();
        instructor.setEmail(dto.email());
        instructor.setContrasenia(dto.password());
        try {
            String token = authenticathionService.authenticate(instructor);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/courses", produces = "application/json")
    public ResponseEntity<?> getCourses(
            @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            Instructor instructor = authorizationService.authorize(token);
            List<InstructorCoursesResponseDTO> courses =
                    courseService.getCoursesByInstructorId(instructor.getId());
            return ResponseEntity.status(HttpStatus.OK).body(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}