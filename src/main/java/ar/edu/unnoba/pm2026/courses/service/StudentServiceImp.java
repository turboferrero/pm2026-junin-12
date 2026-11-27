package ar.edu.unnoba.pm2026.courses.service;

import ar.edu.unnoba.pm2026.courses.dto.CreateEnrollmentRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.CreateStudentRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.EnrollmentResponseDTO;
import ar.edu.unnoba.pm2026.courses.dto.StudentAuthenticationRequestDTO;
import ar.edu.unnoba.pm2026.courses.dto.StudentCourseResponseDTO;
import ar.edu.unnoba.pm2026.courses.model.Course;
import ar.edu.unnoba.pm2026.courses.model.Enrollment;
import ar.edu.unnoba.pm2026.courses.model.Speciality;
import ar.edu.unnoba.pm2026.courses.model.Student;
import ar.edu.unnoba.pm2026.courses.repository.CourseRepository;
import ar.edu.unnoba.pm2026.courses.repository.EnrollmentRepository;
import ar.edu.unnoba.pm2026.courses.repository.StudentRepository;
import ar.edu.unnoba.pm2026.courses.util.JwtTokenUtil;
import ar.edu.unnoba.pm2026.courses.util.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

    private static final BigDecimal FIDELITY_DISCOUNT_RATE = new BigDecimal("0.25");
    private static final BigDecimal PAYABLE_RATE_WITH_DISCOUNT = new BigDecimal("0.75");

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImp(StudentRepository studentRepository,
                             CourseRepository courseRepository,
                             EnrollmentRepository enrollmentRepository,
                             JwtTokenUtil jwtTokenUtil) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = new PasswordEncoder();
    }

    @Override
    public void register(CreateStudentRequestDTO dto) {
        if (dto == null || isBlank(dto.email()) || isBlank(dto.password())
                || isBlank(dto.dni()) || isBlank(dto.name()) || isBlank(dto.lastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos incompletos");
        }
        if (studentRepository.existsByEmail(dto.email()) || studentRepository.existsByDni(dto.dni())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email o DNI ya registrado");
        }

        Student student = new Student();
        student.setNombre(dto.name());
        student.setApellido(dto.lastName());
        student.setDni(dto.dni());
        student.setEmail(dto.email());
        student.setContrasenia(passwordEncoder.encode(dto.password()));
        studentRepository.save(student);
    }

    @Override
    public String authenticate(StudentAuthenticationRequestDTO dto) {
        if (dto == null || isBlank(dto.email()) || isBlank(dto.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        Student student = studentRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!passwordEncoder.verify(dto.password(), student.getContrasenia())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        return jwtTokenUtil.generateToken(student.getEmail());
    }

    @Override
    public Student authorize(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()
                || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token faltante o inválido");
        }
        if (!jwtTokenUtil.verify(authorizationHeader)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token inválido o expirado");
        }
        String email = jwtTokenUtil.getSubject(authorizationHeader);
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token inválido");
        }
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "El token no pertenece a un estudiante"));
    }

    @Override
    public List<StudentCourseResponseDTO> getAvailableCourses(Student student) {
        LocalDate today = LocalDate.now();
        List<Enrollment> studentEnrollments = enrollmentRepository.findByStudentId(student.getId());

        return courseRepository.findAll().stream()
                .filter(this::isPublished)
                .filter(course -> hasFutureStartDate(course.getFechaInicio(), today))
                .filter(course -> getAvailableSeats(course) > 0)
                .filter(course -> !isEnrolledInCourse(studentEnrollments, course.getId()))
                .map(course -> toCourseResponseDTO(course, studentEnrollments, false))
                .toList();
    }

    @Override
    public StudentCourseResponseDTO getCourseDetail(Student student, long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        if (!isPublished(course)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El curso no está publicado");
        }

        List<Enrollment> studentEnrollments = enrollmentRepository.findByStudentId(student.getId());
        boolean enrolled = isEnrolledInCourse(studentEnrollments, courseId);
        return toCourseResponseDTO(course, studentEnrollments, enrolled);
    }

    @Override
    public EnrollmentResponseDTO enroll(Student student, CreateEnrollmentRequestDTO dto) {
        if (dto == null || dto.courseId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "courseId es obligatorio");
        }

        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        if (!isPublished(course) || !course.permiteInscripciones()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El curso no admite inscripciones");
        }

        LocalDate today = LocalDate.now();
        if (course.getFechaInicio() != null && !hasFutureStartDate(course.getFechaInicio(), today)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha de inicio ya venció");
        }
        if (course.getFechaCierre() != null && toLocalDate(course.getFechaCierre()).isBefore(today)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha de inscripción venció");
        }
        if (getAvailableSeats(course) <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cupo alcanzado");
        }
        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya estás inscripto en este curso");
        }

        List<Enrollment> studentEnrollments = enrollmentRepository.findByStudentId(student.getId());
        BigDecimal basePrice = safePrice(course.getPrecioBase());
        boolean appliesFidelity = hasFidelityDiscount(studentEnrollments, course, new Date());
        BigDecimal discountAmount = appliesFidelity
                ? basePrice.multiply(FIDELITY_DISCOUNT_RATE).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalPrice = basePrice.subtract(discountAmount).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);

        Enrollment enrollment = new Enrollment();
        enrollment.setEstudiante(student);
        enrollment.setCurso(course);
        enrollment.setFechaInscripcion(new Date());
        enrollment.setPrecioFinal(finalPrice);

        Enrollment saved = enrollmentRepository.save(enrollment);

        return new EnrollmentResponseDTO(
                saved.getId(),
                course.getId(),
                course.getTitulo(),
                saved.getFechaInscripcion(),
                basePrice.setScale(2, RoundingMode.HALF_UP),
                discountAmount,
                finalPrice
        );
    }

    @Override
    public List<EnrollmentResponseDTO> getMyEnrollments(Student student) {
        return enrollmentRepository.findByStudentIdOrderByFechaInscripcionDesc(student.getId()).stream()
                .map(enrollment -> {
                    Course course = enrollment.getCurso();
                    BigDecimal basePrice = course != null ? safePrice(course.getPrecioBase()).setScale(2, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                    BigDecimal finalPrice = safePrice(enrollment.getPrecioFinal()).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal discount = basePrice.subtract(finalPrice).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
                    return new EnrollmentResponseDTO(
                            enrollment.getId(),
                            course != null ? course.getId() : 0L,
                            course != null ? course.getTitulo() : "",
                            enrollment.getFechaInscripcion(),
                            basePrice,
                            discount,
                            finalPrice
                    );
                })
                .toList();
    }

    private StudentCourseResponseDTO toCourseResponseDTO(Course course, List<Enrollment> enrollments, boolean enrolled) {
        BigDecimal basePrice = safePrice(course.getPrecioBase()).setScale(2, RoundingMode.HALF_UP);
        Date referenceDate = course.getFechaInicio() != null ? course.getFechaInicio() : new Date();
        boolean appliesFidelity = hasFidelityDiscount(enrollments, course, referenceDate);
        BigDecimal applicablePrice = appliesFidelity
                ? basePrice.multiply(PAYABLE_RATE_WITH_DISCOUNT).setScale(2, RoundingMode.HALF_UP)
                : basePrice;

        String instructorName = "";
        if (course.getCreador() != null) {
            instructorName = (nullToEmpty(course.getCreador().getNombre()) + " " + nullToEmpty(course.getCreador().getApellido())).trim();
        }

        return new StudentCourseResponseDTO(
                course.getId(),
                course.getTitulo(),
                course.getDescripcion(),
                course.getFechaInicio(),
                course.getFechaCierre(),
                course.getCupoMaximoAlumnos(),
                getAvailableSeats(course),
                course.getEspecialidad() != null ? course.getEspecialidad().name() : null,
                instructorName,
                basePrice,
                applicablePrice,
                enrolled
        );
    }

    private boolean hasFidelityDiscount(List<Enrollment> enrollments, Course targetCourse, Date referenceDate) {
        if (targetCourse.getEspecialidad() == null || referenceDate == null || enrollments == null) {
            return false;
        }
        Speciality targetSpeciality = targetCourse.getEspecialidad();
        LocalDate targetMonth = toLocalDate(referenceDate).withDayOfMonth(1);

        return enrollments.stream().anyMatch(e -> {
            if (e.getCurso() == null || e.getCurso().getEspecialidad() != targetSpeciality) {
                return false;
            }
            Date enrolledDate = e.getFechaInscripcion() != null
                    ? e.getFechaInscripcion()
                    : e.getCurso().getFechaInicio();
            if (enrolledDate == null) {
                return false;
            }
            LocalDate enrolledMonth = toLocalDate(enrolledDate).withDayOfMonth(1);
            return enrolledMonth.equals(targetMonth);
        });
    }

    private boolean isPublished(Course course) {
        if (course.getEstadoCurso() == null) {
            return false;
        }
        return "Published".equals(course.getEstadoCurso().getClass().getSimpleName());
    }

    private boolean hasFutureStartDate(Date startDate, LocalDate today) {
        if (startDate == null) {
            return false;
        }
        return toLocalDate(startDate).isAfter(today);
    }

    private int getAvailableSeats(Course course) {
        int enrolledCount = course.getInscripciones() != null ? course.getInscripciones().size() : 0;
        return course.getCupoMaximoAlumnos() - enrolledCount;
    }

    private boolean isEnrolledInCourse(List<Enrollment> enrollments, long courseId) {
        return enrollments != null && enrollments.stream()
                .anyMatch(e -> e.getCurso() != null && e.getCurso().getId() == courseId);
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private BigDecimal safePrice(BigDecimal price) {
        return price != null ? price : BigDecimal.ZERO;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}