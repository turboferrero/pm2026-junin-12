package ar.edu.unnoba.pm2026.courses.model;
import jakarta.persistence.*;
@Entity
@Table(name="administrador")
public class Administrador {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idadministrador")
    private long id;

    private String nombre;
    private String apellido;
    @Column(unique=true, nullable=false)
    private String email;
    @Column(name="contrasena", nullable=false)
    private String contrasenia;

    public Administrador() {
    }

    public Administrador(String apellido, String nombre, String email, String contrasenia) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
