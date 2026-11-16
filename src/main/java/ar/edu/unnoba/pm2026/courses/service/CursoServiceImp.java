package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Curso;
import ar.edu.unnoba.pm2026.courses.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CursoServiceImp implements CursoService{
    private CursoRepository cursoRepository;
    @Override
    public List<InstructorCoursesResponseDTO> getCoursesByInstructorId(Long instructorId) {
      List<Curso> cursos= cursoRepository.findByInstructorId(instructorId);
        List<InstructorCoursesResponseDTO> response = new ArrayList<>();
      for(Curso curso:cursos){
          InstructorCoursesResponseDTO dto= new InstructorCoursesResponseDTO(curso.getId(),curso.getTitulo(),curso.getDescripcion());
          response.add(dto);
      }
      return response;
    }
}
