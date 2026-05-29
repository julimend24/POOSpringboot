package com.universidad.gestion.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estudiante_id")
    private Integer estudianteId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "dni")
    private String dni;

    @Column(name = "aspiranteAGrado")
    private Integer aspiranteAGrado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id")
    private ProyectoDeGrado proyectos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejecucionproyecto_id")
    private EjecucionDelProyecto ejecucionProyecto;

    @OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
    private List<PersonalDocente> docentes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "estudiante_universidad",
        joinColumns = @JoinColumn(name = "estudiante_id"),
        inverseJoinColumns = @JoinColumn(name = "universidad_id")
    )
    private List<Universidad> universidades = new ArrayList<>();

    public Estudiante() {}

    // Getters y Setters
    public Integer getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Integer estudianteId) { this.estudianteId = estudianteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Integer getAspiranteAGrado() { return aspiranteAGrado; }
    public void setAspiranteAGrado(Integer aspiranteAGrado) { this.aspiranteAGrado = aspiranteAGrado; }

    public ProyectoDeGrado getProyectos() { return proyectos; }
    public void setProyectos(ProyectoDeGrado proyectos) { this.proyectos = proyectos; }

    public EjecucionDelProyecto getEjecucionProyecto() { return ejecucionProyecto; }
    public void setEjecucionProyecto(EjecucionDelProyecto ejecucionProyecto) { this.ejecucionProyecto = ejecucionProyecto; }

    public List<PersonalDocente> getDocentes() { return docentes; }
    public void setDocentes(List<PersonalDocente> docentes) { this.docentes = docentes; }

    public List<Universidad> getUniversidades() { return universidades; }
    public void setUniversidades(List<Universidad> universidades) { this.universidades = universidades; }

    // Helpers para las vistas
    public String getNombreCompleto() {
        return (apellido != null ? apellido : "") + ", " + (nombre != null ? nombre : "");
    }

    public String getAspiranteLabel() {
        return (aspiranteAGrado != null && aspiranteAGrado == 1) ? "Sí" : "No";
    }
}
