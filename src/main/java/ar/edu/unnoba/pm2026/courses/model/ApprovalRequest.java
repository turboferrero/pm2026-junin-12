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
    private Estado estado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idcurso", nullable = false)
    private Curso curso;

    public ApprovalRequest() {
    }

    public ApprovalRequest(long id, Curso curso, String comentarioInstructor, Estado estado, String comentarioAdmin, Date fechaResolucion, Date fechaSolicitud) {
        this.id = id;
        this.curso = curso;
        this.comentarioInstructor = comentarioInstructor;
        this.estado = estado;
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
}