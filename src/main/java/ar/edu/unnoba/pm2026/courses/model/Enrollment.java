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
    private Estudiante estudiante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idcurso", nullable = false)
    private Curso curso;

    @Column(name = "fecha_inscripcion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInscripcion;

    @Column(name = "precio_final", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioFinal;

    public Enrollment() {
    }

    public Enrollment(Estudiante estudiante, Curso curso, Date fechaInscripcion, BigDecimal precioFinal) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.fechaInscripcion = fechaInscripcion;
        this.precioFinal = precioFinal;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public Date getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(Date fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public BigDecimal getPrecioFinal() { return precioFinal; }
    public void setPrecioFinal(BigDecimal precioFinal) { this.precioFinal = precioFinal; }
}