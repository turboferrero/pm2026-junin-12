
package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorCoursesDetalleResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.CreateCourseRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.UpdateCourseRequesDTO;
import ar.edu.unnoba.pm2026.courses.dto.CourseStudentResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.InstructorRevenueEnrollmentsResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Enrollment;
import ar.edu.unnoba.pm2026.courses.model.Estudiante;
import ar.edu.unnoba.pm2026.courses.model.Instructor;
import ar.edu.unnoba.pm2026.courses.model.Especialidades;
import ar.edu.unnoba.pm2026.courses.model.Curso;
import ar.edu.unnoba.pm2026.courses.model.Estado;
import ar.edu.unnoba.pm2026.courses.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ar.edu.unnoba.pm2026.courses.dto.ApprovalRequestResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.ApprovalRequestDTO;
import ar.edu.unnoba.pm2026.courses.model.ApprovalRequest;
import java.util.Date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.HashMap;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service

public class CursoServiceImp implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public List<InstructorCoursesResponseDTO> getCoursesByInstructorId(Long instructorId) {
        List<Curso> cursos = cursoRepository.findByCreador_Id(instructorId);
        List<InstructorCoursesResponseDTO> response = new ArrayList<>();

        for (Curso curso : cursos) {
            InstructorCoursesResponseDTO dto = new InstructorCoursesResponseDTO(
                    curso.getId(),
                    curso.getTitulo(),
                    curso.getDescripcion()
            );
            response.add(dto);
        }

        return response;
    }

    @Override
    public InstructorCoursesDetalleResponseDTO getCourseById(Long courseId, Long instructorId) {

        Optional<Curso> cursoEncontrado = cursoRepository.findById(courseId);

        if (cursoEncontrado.isEmpty()) {
            throw new RuntimeException("Curso no encontrado");
        }

        Curso curso = cursoEncontrado.get();

        if (curso.getCreador().getId() != instructorId) {
            throw new RuntimeException("El curso no pertenece al instructor");
        }

        InstructorCoursesDetalleResponseDTO response = new InstructorCoursesDetalleResponseDTO(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescripcion(),
                curso.getFechaInicio(),
                curso.getFechaCierre(),
                curso.getEstadoCurso().toString(),
                curso.getCupoMaximoAlumnos(),
                curso.getInscripciones().size(),
                curso.getPrecioBase(),
                curso.getEspecialidad().toString()
        );

        return response;
    }
    @Override
    public void createCourse(CreateCourseRequestDTO dto, Instructor instructor) {

        Curso curso = new Curso();

        curso.setTitulo(dto.title());
        curso.setDescripcion(dto.description());
        curso.setFechaInicio(dto.startDate());
        curso.setFechaCierre(dto.endDate());
        curso.setCupoMaximoAlumnos(dto.maxCapacity());
        curso.setPrecioBase(dto.basePrice());
        boolean especialidadEncontrada = false;

        for (Especialidades especialidad : Especialidades.values()) {
            if (especialidad.toString().equals(dto.specialty())) {
                curso.setEspecialidad(especialidad);
                especialidadEncontrada = true;
                break;
            }
        }

        if (!especialidadEncontrada) {
            throw new IllegalArgumentException("Especialidad no válida");
        }

        curso.setCreador(instructor);

        cursoRepository.save(curso);
    }
    @Override
    public void updateCourse(Long courseId, Long instructorId, UpdateCourseRequesDTO dto) {

        Optional<Curso> cursoEncontrado = cursoRepository.findById(courseId);

        if (cursoEncontrado.isEmpty()) {
            throw new RuntimeException("Curso no encontrado");
        }

        Curso curso = cursoEncontrado.get();

        if (curso.getCreador().getId() != instructorId) {
            throw new RuntimeException("El curso no pertenece al instructor");
        }

        if (!curso.puedeEditarseOEliminarsePorInstructor()) {
            throw new IllegalStateException("El curso no está en estado borrador");
        }

        curso.setTitulo(dto.title());
        curso.setDescripcion(dto.description());
        curso.setFechaInicio(dto.startDate());
        curso.setFechaCierre(dto.endDate());
        curso.setCupoMaximoAlumnos(dto.maxCapacity());
        curso.setPrecioBase(dto.basePrice());

        boolean especialidadEncontrada = false;

        for (Especialidades especialidad : Especialidades.values()) {
            if (especialidad.toString().equals(dto.specialty())) {
                curso.setEspecialidad(especialidad);
                especialidadEncontrada = true;
                break;
            }
        }

        if (!especialidadEncontrada) {
            throw new IllegalArgumentException("Especialidad no válida");
        }

        cursoRepository.save(curso);
    }
    @Override
    public void deleteCourse(Long courseId, Long instructorId) {

        Optional<Curso> cursoEncontrado = cursoRepository.findById(courseId);

        if (cursoEncontrado.isEmpty()) {
            throw new RuntimeException("Curso no encontrado");
        }

        Curso curso = cursoEncontrado.get();

        if (curso.getCreador().getId() != instructorId) {
            throw new RuntimeException("El curso no pertenece al instructor");
        }

        if (!curso.puedeEditarseOEliminarsePorInstructor()) {
            throw new IllegalStateException("El curso no está en estado borrador");
        }

        cursoRepository.delete(curso);
    }
    @Override
    public void requestApproval(Long courseId, Long instructorId, ApprovalRequestDTO dto) {

        Optional<Curso> cursoEncontrado = cursoRepository.findById(courseId);

        if (cursoEncontrado.isEmpty()) {
            throw new RuntimeException("Curso no encontrado");
        }

        Curso curso = cursoEncontrado.get();

        if (curso.getCreador().getId() != instructorId) {
            throw new RuntimeException("El curso no pertenece al instructor");
        }

        ApprovalRequest solicitud = new ApprovalRequest();

        solicitud.setCurso(curso);
        solicitud.setFechaSolicitud(new Date());
        solicitud.setComentarioInstructor(dto != null ? dto.instructorComment() : null);
        solicitud.setEstado(Estado.SUBMITTED);

        curso.getSolicitudesAprobacion().add(solicitud);

        curso.solicitarAprobacion();

        cursoRepository.save(curso);
    }
    @Override
    public List<ApprovalRequestResponseDTO> getApprovalRequestsByInstructorId(Long instructorId) {

        List<Curso> cursos = cursoRepository.findByCreador_Id(instructorId);

        List<ApprovalRequestResponseDTO> response = new ArrayList<>();

        for (Curso curso : cursos) {

            for (ApprovalRequest solicitud : curso.getSolicitudesAprobacion()) {

                ApprovalRequestResponseDTO dto = new ApprovalRequestResponseDTO(
                        solicitud.getId(),
                        curso.getId(),
                        curso.getTitulo(),
                        solicitud.getFechaSolicitud(),
                        solicitud.getComentarioInstructor(),
                        solicitud.getEstado().toString()
                );

                response.add(dto);
            }
        }

        return response;
    }
    @Override
    public List<CourseStudentResponseDTO> getStudentsByCourseId(Long courseId, Long instructorId) {

        Optional<Curso> optionalCurso = cursoRepository.findById(courseId);

        if (optionalCurso.isEmpty()) {
            throw new RuntimeException("Curso no encontrado");
        }

        Curso curso = optionalCurso.get();

        if (curso.getCreador().getId() != instructorId) {
            throw new RuntimeException("El curso no pertenece al instructor");
        }

        List<CourseStudentResponseDTO> students = new ArrayList<>();

        for (Enrollment enrollment : curso.getInscripciones()) {

            Estudiante estudiante = enrollment.getEstudiante();

            CourseStudentResponseDTO dto = new CourseStudentResponseDTO(
                    estudiante.getId(),
                    estudiante.getNombre(),
                    estudiante.getApellido(),
                    estudiante.getDni(),
                    estudiante.getEmail(),
                    enrollment.getFechaInscripcion(),
                    enrollment.getPrecioFinal()
            );

            students.add(dto);
        }

        return students;
    }
    @Override
    public BigDecimal getInstructorRevenue(Long instructorId) {

        List<Curso> cursos = cursoRepository.findByCreador_Id(instructorId);

        BigDecimal total = BigDecimal.ZERO;

        for (Curso curso : cursos) {

            for (Enrollment enrollment : curso.getInscripciones()) {

                BigDecimal ingreso = curso.getPrecioBase()
                        .multiply(new BigDecimal("0.65"));

                total = total.add(ingreso);
            }
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
    @Override
    public InstructorRevenueEnrollmentsResponseDTO getInstructorRevenueEnrollments(
            Long instructorId, LocalDate fromDate, LocalDate toDate) {

        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final");
        }

        List<Curso> cursos = cursoRepository.findByCreador_Id(instructorId);

        List<Map<String, Object>> enrollments = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Curso curso : cursos) {

            for (Enrollment enrollment : curso.getInscripciones()) {

                Date fecha = enrollment.getFechaInscripcion();

                LocalDate fechaInscripcion = fecha.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if (fromDate != null && fechaInscripcion.isBefore(fromDate)) {
                    continue;
                }

                if (toDate != null && fechaInscripcion.isAfter(toDate)) {
                    continue;
                }

                BigDecimal monto = curso.getPrecioBase()
                        .multiply(new BigDecimal("0.65"))
                        .setScale(2, RoundingMode.HALF_UP);

                Map<String, Object> detalle = new HashMap<>();
                detalle.put("enrollmentDate", fechaInscripcion);
                detalle.put("courseId", curso.getId());
                detalle.put("courseTitle", curso.getTitulo());
                detalle.put("amount", monto);

                enrollments.add(detalle);
                total = total.add(monto);
            }
        }

        return new InstructorRevenueEnrollmentsResponseDTO(
                total.setScale(2, RoundingMode.HALF_UP),
                enrollments
        );
    }
}
