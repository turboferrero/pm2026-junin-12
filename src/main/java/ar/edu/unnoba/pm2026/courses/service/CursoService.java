package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Curso;

import java.util.List;

public interface CursoService {

    public List<InstructorCoursesResponseDTO> getCoursesByInstructorId(Long instructorId);

}
