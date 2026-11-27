package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.CourseDetailResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.CourseResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.EnrolledStudentResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Course;
import ar.edu.unnoba.pm2026.courses.model.Enrollment;
import ar.edu.unnoba.pm2026.courses.model.Published;
import ar.edu.unnoba.pm2026.courses.repository.CourseRepository;
import ar.edu.unnoba.pm2026.courses.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseServiceImp implements CourseService {
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;
    public CourseServiceImp(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }
    @Override
    public List<InstructorCoursesResponseDTO> getCoursesByInstructorId(Long instructorId) {
      List<Course> courses = courseRepository.findByCreadorId(instructorId);
        List<InstructorCoursesResponseDTO> response = new ArrayList<>();
      for(Course course : courses){
          InstructorCoursesResponseDTO dto= new InstructorCoursesResponseDTO(course.getId(), course.getTitulo(), course.getDescripcion());
          response.add(dto);
      }
      return response;
    }

    @Override
    public List<CourseResponseDTO> getApprovedCourses() {
        List<CourseResponseDTO> response = new ArrayList<>();
        List<Course> courses = courseRepository.findByEstadoOrderByFechaInicioDesc(new Published());
        for(Course course : courses){
            response.add(CourseResponseDTO.from(course));
        }
        return response;
    }

    @Override
    public List<EnrolledStudentResponseDTO> getEnrolledStudents(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new NoSuchElementException("No existe un curso con el ID proporcionado");
        }
        List<Enrollment> inscripciones = enrollmentRepository.findByCursoId(courseId);
        List<EnrolledStudentResponseDTO> response = new ArrayList<>();

        for (Enrollment inscripcion : inscripciones) {
            response.add(EnrolledStudentResponseDTO.from(inscripcion));
        }

        return response;
    }

    // En CourseServiceImp:
    @Override
    public CourseDetailResponseDTO getCourseDetail(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe un curso con el ID proporcionado"));

        return CourseDetailResponseDTO.from(course);
    }
}
