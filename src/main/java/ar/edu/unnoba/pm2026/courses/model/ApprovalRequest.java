package ar.edu.unnoba.pm2026.courses.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "approval_request")
public class ApprovalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idapprovalrequest")
    private long id;

    @Column(name = "fecha_solicitud", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;


    @Column(name = "fecha_resolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaResolucion;

    @Column(name = "comentario_instructor")
    private String comentarioInstructor;

    @Column(name = "comentario_admin")
    private String comentarioAdmin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idcurso", nullable = false)
    private Course course;

    public ApprovalRequest() {
    }

    public ApprovalRequest(long id, Course course, String comentarioInstructor, State state, String comentarioAdmin, Date fechaResolucion, Date fechaSolicitud) {
        this.id = id;
        this.course = course;
        this.comentarioInstructor = comentarioInstructor;
        this.state = state;
        this.comentarioAdmin = comentarioAdmin;
        this.fechaResolucion = fechaResolucion;
        this.fechaSolicitud = fechaSolicitud;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Date getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(Date fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public Date getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(Date fechaResolucion) { this.fechaResolucion = fechaResolucion; }

    public String getComentarioInstructor() { return comentarioInstructor; }
    public void setComentarioInstructor(String comentarioInstructor) { this.comentarioInstructor = comentarioInstructor; }

    public String getComentarioAdmin() { return comentarioAdmin; }
    public void setComentarioAdmin(String comentarioAdmin) { this.comentarioAdmin = comentarioAdmin; }

    public Course getCurso() {
        return course;
    }

    public void setCurso(Course course) {
        this.course = course;
    }

    public State getEstado() { return state; }
    public void setEstado(State state) { this.state = state; }
}