package ar.edu.unnoba.pm2026.courses.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(
        name = "enrollment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idestudiante", "idcurso"})
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idenrollment")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idestudiante", nullable = false)
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idcurso", nullable = false)
    private Course course;

    @Column(name = "fecha_inscripcion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInscripcion;

    @Column(name = "precio_final", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioFinal;

    public Enrollment() {
    }

    public Enrollment(Student student, Course course, Date fechaInscripcion, BigDecimal precioFinal) {
        this.student = student;
        this.course = course;
        this.fechaInscripcion = fechaInscripcion;
        this.precioFinal = precioFinal;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Student getEstudiante() { return student; }
    public void setEstudiante(Student student) { this.student = student; }

    public Course getCurso() { return course; }
    public void setCurso(Course course) { this.course = course; }

    public Date getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(Date fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public BigDecimal getPrecioFinal() { return precioFinal; }
    public void setPrecioFinal(BigDecimal precioFinal) { this.precioFinal = precioFinal; }
}