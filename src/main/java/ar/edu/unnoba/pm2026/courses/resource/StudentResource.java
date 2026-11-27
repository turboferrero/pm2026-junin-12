package ar.edu.unnoba.pm2026.courses.resource;

import ar.edu.unnoba.pm2026.courses.dto.CreateEnrollmentRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.CreateStudentRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.EnrollmentResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.StudentAuthenticationRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.StudentCourseResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Student;
import ar.edu.unnoba.pm2026.courses.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class StudentResource {

    private final StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "/students", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody CreateStudentRequestDTO dto) {
        try {
            studentService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason() != null ? e.getReason() : ""));
        }
    }

    @PostMapping(value = "/students/auth", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> authenticate(@RequestBody StudentAuthenticationRequestDTO dto) {
        try {
            String token = studentService.authenticate(dto);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason() != null ? e.getReason() : ""));
        }
    }

    @GetMapping(value = "/student/courses/available", produces = "application/json")
    public ResponseEntity<?> getAvailableCourses(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            Student student = studentService.authorize(authorization);
            List<StudentCourseResponseDTO> courses = studentService.getAvailableCourses(student);
            return ResponseEntity.status(HttpStatus.OK).body(courses);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason() != null ? e.getReason() : ""));
        }
    }

    @GetMapping(value = "/student/courses/{id}", produces = "application/json")
    public ResponseEntity<?> getCourseDetail(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable("id") long id) {
        try {
            Student student = studentService.authorize(authorization);
            StudentCourseResponseDTO courseDetail = studentService.getCourseDetail(student, id);
            return ResponseEntity.status(HttpStatus.OK).body(courseDetail);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason() != null ? e.getReason() : ""));
        }
    }

    @PostMapping(value = "/student/enrollments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> enroll(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody CreateEnrollmentRequestDTO dto) {
        try {
            Student student = studentService.authorize(authorization);
            EnrollmentResponseDTO enrollment = studentService.enroll(student, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason() != null ? e.getReason() : ""));
        }
    }

    @GetMapping(value = "/students/enrollments", produces = "application/json")
    public ResponseEntity<?> getMyEnrollments(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            Student student = studentService.authorize(authorization);
            List<EnrollmentResponseDTO> enrollments = studentService.getMyEnrollments(student);
            return ResponseEntity.status(HttpStatus.OK).body(enrollments);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason() != null ? e.getReason() : ""));
        }
    }
}


