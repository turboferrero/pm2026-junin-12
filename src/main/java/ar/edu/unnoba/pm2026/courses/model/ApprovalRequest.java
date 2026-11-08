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

    public ApprovalRequest() {
    }

    public ApprovalRequest(Date fechaSolicitud, Date fechaResolucion, String comentarioInstructor,
                           String comentarioAdmin, Estado estado) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaResolucion = fechaResolucion;
        this.comentarioInstructor = comentarioInstructor;
        this.comentarioAdmin = comentarioAdmin;
        this.estado = estado;
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

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
}