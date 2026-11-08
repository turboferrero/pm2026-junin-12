package ar.edu.unnoba.pm2026.courses.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "curso")
public class Curso {
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idcurso", nullable = false)
    private List<ApprovalRequest> solicitudesAprovacion = new ArrayList<>();

    @Convert(converter = EstadoCursoConverter.class)
    @Column(name = "estado", nullable = false)
    private EstadoCurso estadoCurso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idinstructor")
    private Instructor creador;

    @Enumerated(EnumType.STRING)
    private Especialidades especialidad;

    @ManyToMany(mappedBy = "cursos")
    private List<Estudiante> estudiantes = new ArrayList<>();

    public Curso() {
        this.estadoCurso = new Borrador(this);
    }

    public Curso(String titulo, String descripcion, Date fechaInicio, BigDecimal precioBase,
                 Date fechaCierre, int cupoMaximoAlumnos,
                 List<ApprovalRequest> solicitudesAprovacion, EstadoCurso estadoCurso,
                 Instructor creador, Especialidades especialidad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.precioBase = precioBase;
        this.fechaCierre = fechaCierre;
        this.cupoMaximoAlumnos = cupoMaximoAlumnos;
        this.solicitudesAprovacion = solicitudesAprovacion;
        this.estadoCurso = estadoCurso;
        this.creador = creador;
        this.especialidad = especialidad;
    }

    @PostLoad
    private void vincularEstado() {
        if (estadoCurso != null) {
            estadoCurso.setCurso(this);
        }
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

    public List<ApprovalRequest> getSolicitudesAprovacion() {
        return solicitudesAprovacion;
    }

    public void setSolicitudesAprovacion(List<ApprovalRequest> solicitudesAprovacion) {
        this.solicitudesAprovacion = solicitudesAprovacion;
    }

    public EstadoCurso getEstadoCurso() {
        return estadoCurso;
    }

    public void setEstadoCurso(EstadoCurso estadoCurso) {
        this.estadoCurso = estadoCurso;
    }

    public Instructor getCreador() {
        return creador;
    }

    public void setCreador(Instructor creador) {
        this.creador = creador;
    }

    public Especialidades getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidades especialidad) {
        this.especialidad = especialidad;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
}