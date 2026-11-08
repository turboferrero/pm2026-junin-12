package ar.edu.unnoba.pm2026.courses.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="instructor")
public class Instructor {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idinstructor")
    private long id;
    private String nombre;
    private String apellido;
    @Column(unique=true, nullable=false)
    private String email;
    @Column(name = "contrasena", nullable = false)
    private String contrasenia;


    @ElementCollection
    @CollectionTable(name = "instructor_especialidad",
            joinColumns = @JoinColumn(name = "idinstructor"))
    @Column(name = "especialidad")
    @Enumerated(EnumType.STRING)
    private List<Especialidades> especialidades = new ArrayList<>();

    @OneToMany(mappedBy = "creador")
    private List<Curso> cursos = new ArrayList<>();

    public Instructor() {
    }

    public Instructor(String nombre, String apellido, String email, String contrasenia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public void agregarEspecialidad(Especialidades especialidad) {
        if (!especialidades.contains(especialidad)) {
            especialidades.add(especialidad);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Especialidades> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidades> especialidades) {
        this.especialidades = especialidades;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
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
