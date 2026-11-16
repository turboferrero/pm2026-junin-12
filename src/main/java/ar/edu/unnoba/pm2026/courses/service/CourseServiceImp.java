package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Course;
import ar.edu.unnoba.pm2026.courses.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImp implements CourseService {
    private CourseRepository courseRepository;
    @Override
    public List<InstructorCoursesResponseDTO> getCoursesByInstructorId(Long instructorId) {
      List<Course> courses = courseRepository.findByInstructorId(instructorId);
        List<InstructorCoursesResponseDTO> response = new ArrayList<>();
      for(Course course : courses){
          InstructorCoursesResponseDTO dto= new InstructorCoursesResponseDTO(course.getId(), course.getTitulo(), course.getDescripcion());
          response.add(dto);
      }
      return response;
    }
}
