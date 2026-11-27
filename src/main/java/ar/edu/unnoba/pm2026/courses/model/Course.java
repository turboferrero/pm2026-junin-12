package ar.edu.unnoba.pm2026.courses.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "curso")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcurso")
    private long id;

    private String titulo;
    private String descripcion;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.DATE)
    private Date fechaCierre;

    @Column(name = "precio_base", precision = 10, scale = 2)
    private BigDecimal precioBase;

    @Column(name = "cupo_maximo_alumnos")
    private int cupoMaximoAlumnos;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalRequest> solicitudesAprobacion = new ArrayList<>();

    @Convert(converter = CourseStateConverter.class)
    @Column(name = "estado", nullable = false)
    private CourseState courseState;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idinstructor")
    private Instructor creador;

    @Enumerated(EnumType.STRING)
    private Speciality especialidad;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> inscripciones = new ArrayList<>();

    public Course() {
        this.courseState = new Draft(this);
    }

    public Course(String titulo, String descripcion, Date fechaInicio, BigDecimal precioBase,
                  Date fechaCierre, int cupoMaximoAlumnos,
                  List<ApprovalRequest> solicitudesAprobacion, CourseState courseState,
                  Instructor creador, Speciality especialidad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.precioBase = precioBase;
        this.fechaCierre = fechaCierre;
        this.cupoMaximoAlumnos = cupoMaximoAlumnos;
        this.solicitudesAprobacion = solicitudesAprobacion;
        setEstadoCurso(courseState);
        this.creador = creador;
        this.especialidad = especialidad;
    }

    @PostLoad
    private void vincularEstado() {
        if (this.courseState != null) {
            this.courseState.setCurso(this);
        }
    }
    public void solicitarAprobacion() {
        this.courseState.solicitarAprobacion();
    }

    public void aprobar() {
        this.courseState.aprobar();
    }

    public void rechazar() {
        this.courseState.rechazar();
    }

    public boolean puedeEditarseOEliminarsePorInstructor() {
        return this.courseState.puedeEditarseOEliminarsePorInstructor();
    }

    public boolean permiteInscripciones() {
        return this.courseState.permiteInscripciones();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getCupoMaximoAlumnos() {
        return cupoMaximoAlumnos;
    }

    public void setCupoMaximoAlumnos(int cupoMaximoAlumnos) {
        this.cupoMaximoAlumnos = cupoMaximoAlumnos;
    }

    public List<ApprovalRequest> getSolicitudesAprobacion() {
        return solicitudesAprobacion;
    }

    public void setSolicitudesAprobacion(List<ApprovalRequest> solicitudesAprobacion) {
        this.solicitudesAprobacion = solicitudesAprobacion;
    }

    public CourseState getEstadoCurso() {
        return courseState;
    }

    public void setEstadoCurso(CourseState courseState) {
        this.courseState = courseState;
        if (this.courseState != null) {
            this.courseState.setCurso(this);
        }
    }

    public Instructor getCreador() {
        return creador;
    }

    public void setCreador(Instructor creador) {
        this.creador = creador;
    }

    public Speciality getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Speciality especialidad) {
        this.especialidad = especialidad;
    }

    public List<Enrollment> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Enrollment> inscripciones) {
        this.inscripciones = inscripciones;
    }
    public String getEmailInstructor() {
        return getCreador() != null ? getCreador().getEmail() : null;
    }
}