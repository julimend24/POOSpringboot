package com.universidad.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "personal_docente")
public class PersonalDocente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docentes_id")
    private Integer docentesId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "trayectoria")
    private String trayectoria;

    @Column(name = "conocimiento")
    private String conocimiento;

    @Column(name = "experiencia")
    private String experiencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejecucionproyecto_id")
    private EjecucionDelProyecto ejecucionproyecto;

    public PersonalDocente() {}

    public Integer getDocentesId() { return docentesId; }
    public void setDocentesId(Integer id) { this.docentesId = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTrayectoria() { return trayectoria; }
    public void setTrayectoria(String trayectoria) { this.trayectoria = trayectoria; }

    public String getConocimiento() { return conocimiento; }
    public void setConocimiento(String conocimiento) { this.conocimiento = conocimiento; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public EjecucionDelProyecto getEjecucionproyecto() { return ejecucionproyecto; }
    public void setEjecucionproyecto(EjecucionDelProyecto e) { this.ejecucionproyecto = e; }

    public String getNombreCompleto() {
        return (apellido != null ? apellido : "") + ", " + (nombre != null ? nombre : "");
    }
}
