package ar.edu.unnoba.pm2026.courses.model;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "estudiante")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestudiante")
    private long id;
    private String nombre;
    private String apellido;
    @Column(unique = true, nullable = false)
    private String dni;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(name="contrasena",nullable = false)
    private String contrasenia;
    @ManyToMany
    @JoinTable(name = "estudiante_curso",
    joinColumns = @JoinColumn(name = "estudianteid"),
    inverseJoinColumns = @JoinColumn(name = "cursoid"))
    private List<Curso> cursos = new ArrayList<>();

    public Estudiante() {
    }

    public Estudiante(String nombre, String apellido, String dni, String email, String contrasenia, List<Curso> cursos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.contrasenia = contrasenia;
        this.cursos = cursos;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
