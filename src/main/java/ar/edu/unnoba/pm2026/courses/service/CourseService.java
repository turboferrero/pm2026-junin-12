package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;

import java.util.List;

public interface CourseService {

    public List<InstructorCoursesResponseDTO> getCoursesByInstructorId(Long instructorId);

}
