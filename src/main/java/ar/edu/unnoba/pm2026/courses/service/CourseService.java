package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.CourseDetailResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.CourseResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.EnrolledStudentResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;

import java.util.List;

public interface CourseService {

    public List<InstructorCoursesResponseDTO> getCoursesByInstructorId(Long instructorId);
    public List<CourseResponseDTO> getApprovedCourses();
    public List<EnrolledStudentResponseDTO> getEnrolledStudents(Long courseId);
    public CourseDetailResponseDTO getCourseDetail(Long id);
}
