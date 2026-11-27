package ar.edu.unnoba.pm2026.courses.resource;

import ar.edu.unnoba.pm2026.courses.dto.CreateInstructorRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorAuthenticationRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesDetalleResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.CreateCourseRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.ApprovalRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.ApprovalRequestResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Instructor;

import ar.edu.unnoba.pm2026.courses.service.AuthenticathionService;
import ar.edu.unnoba.pm2026.courses.service.AuthorizationService;
import ar.edu.unnoba.pm2026.courses.service.InstructorService;
import ar.edu.unnoba.pm2026.courses.service.CursoService;

import org.modelmapper.ModelMapper;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.pm2026.courses.dto.UpdateCourseRequesDTO;
import ar.edu.unnoba.pm2026.courses.dto.CourseStudentResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorRevenueEnrollmentsResponseDTO;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instructor")
public class InstructorResource {

    private final InstructorService instructorService;
    private final ModelMapper modelMapper;
    private final AuthenticathionService authenticathionService;
    private final AuthorizationService authorizationService;
    private final CursoService cursoService;

    public InstructorResource(InstructorService instructorService, ModelMapper modelMapper, AuthenticathionService authenticathionService, AuthorizationService authorizationService, CursoService cursoService) {

        this.instructorService = instructorService;
        this.modelMapper = modelMapper;
        this.authenticathionService = authenticathionService;
        this.authorizationService = authorizationService;
        this.cursoService = cursoService;
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
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateInstructorRequestDTO dto) {
        Instructor instructor = modelMapper.map(dto, Instructor.class);
        instructor.setContrasenia(dto.password());
        try {
            instructorService.create(instructor);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping(value = "/courses", produces = "application/json")
    public ResponseEntity<?> getCourses(
            @RequestHeader("Authorization") String token) {

        try {

            Instructor instructor = authorizationService.authorize(token);


            List<InstructorCoursesResponseDTO> courses =
                    cursoService.getCoursesByInstructorId(instructor.getId());


            return ResponseEntity.status(HttpStatus.OK).body(courses);

        } catch (Exception e) {


            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping(value = "/courses/{id}", produces = "application/json")
    public ResponseEntity<?> getCourse(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        Instructor instructor;

        try {
            instructor = authorizationService.authorize(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            InstructorCoursesDetalleResponseDTO course =
                    cursoService.getCourseById(id, instructor.getId());

            return ResponseEntity.status(HttpStatus.OK).body(course);

        } catch (RuntimeException e) {

            if ("Curso no encontrado".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @PostMapping(value = "/courses", consumes = "application/json")
    public ResponseEntity<?> createCourse(
            @RequestBody CreateCourseRequestDTO dto,
            @RequestHeader("Authorization") String token) {

        Instructor instructor;

        try {
            instructor = authorizationService.authorize(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            cursoService.createCourse(dto, instructor);

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateCourseRequesDTO dto) {

        Instructor instructor;

        try {
            instructor = authorizationService.authorize(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            cursoService.updateCourse(id, instructor.getId(), dto);
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (RuntimeException e) {
            if ("Curso no encontrado".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        Instructor instructor;

        try {
            instructor = authorizationService.authorize(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            cursoService.deleteCourse(id, instructor.getId());
            return ResponseEntity.noContent().build();

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (RuntimeException e) {
            if ("Curso no encontrado".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @PostMapping("/courses/{id}/approval-requests")
    public ResponseEntity<?> requestApproval(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token,
            @RequestBody(required = false) ApprovalRequestDTO dto) {

        Instructor instructor;

        try {
            instructor = authorizationService.authorize(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            cursoService.requestApproval(id, instructor.getId(), dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (RuntimeException e) {
            if ("Curso no encontrado".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @GetMapping("/approval-requests")
    public ResponseEntity<?> getApprovalRequests(
            @RequestHeader("Authorization") String token) {

        try {
            Instructor instructor = authorizationService.authorize(token);

            List<ApprovalRequestResponseDTO> solicitudes =
                    cursoService.getApprovalRequestsByInstructorId(instructor.getId());

            return ResponseEntity.ok(solicitudes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/courses/{id}/students")
    public ResponseEntity<?> getCourseStudents(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        Instructor instructor;

        try {
            instructor = authorizationService.authorize(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            List<CourseStudentResponseDTO> students =
                    cursoService.getStudentsByCourseId(id, instructor.getId());

            return ResponseEntity.ok(students);

        } catch (RuntimeException e) {

            if ("Curso no encontrado".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}