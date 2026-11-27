package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.CreateCourseRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesDetalleResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.UpdateCourseRequesDTO;
import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.dto.ApprovalRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.ApprovalRequestResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.CourseStudentResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import ar.edu.unnoba.pm2026.courses.dto.InstructorRevenueEnrollmentsResponseDTO;
import java.util.List;

public interface CursoService {

    public List<InstructorCoursesResponseDTO> getCoursesByInstructorId(Long instructorId);

    public InstructorCoursesDetalleResponseDTO getCourseById(Long courseId, Long instructorId);

    public void createCourse(CreateCourseRequestDTO dto, Instructor instructor);

    public void updateCourse(Long courseId, Long instructorId, UpdateCourseRequesDTO dto);

    public void deleteCourse(Long courseId, Long instructorId);

    public void requestApproval(Long courseId, Long instructorId, ApprovalRequestDTO dto);

    public List<ApprovalRequestResponseDTO> getApprovalRequestsByInstructorId(Long instructorId);

    public List<CourseStudentResponseDTO> getStudentsByCourseId(Long courseId, Long instructorId);

    public BigDecimal getInstructorRevenue(Long instructorId);

    public InstructorRevenueEnrollmentsResponseDTO getInstructorRevenueEnrollments(Long instructorId, LocalDate fromDate, LocalDate toDate);
}