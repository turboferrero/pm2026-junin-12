package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.CreateEnrollmentRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.CreateStudentRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.EnrollmentResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.StudentAuthenticationRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.StudentCourseResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Student;

import java.util.List;

public interface StudentService {
    void register(CreateStudentRequestDTO dto);
    String authenticate(StudentAuthenticationRequestDTO dto);
    Student authorize(String authorizationHeader);
    List<StudentCourseResponseDTO> getAvailableCourses(Student student);
    StudentCourseResponseDTO getCourseDetail(Student student, long courseId);
    EnrollmentResponseDTO enroll(Student student, CreateEnrollmentRequestDTO dto);
    List<EnrollmentResponseDTO> getMyEnrollments(Student student);
}
