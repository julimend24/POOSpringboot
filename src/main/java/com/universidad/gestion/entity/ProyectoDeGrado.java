package com.universidad.gestion.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proyecto_de_grado")
public class ProyectoDeGrado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proyecto_id")
    private Integer proyectoId;

    @Column(name = "alcance")
    private String alcance;

    @Column(name = "objetivo")
    private String objetivo;

    @Column(name = "planDeProyecto")
    private String planDeProyecto;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "costo")
    private Integer costo;

    @Column(name = "tiempoDeRealizacion")
    private String tiempoDeRealizacion;

    @Column(name = "viabilidad")
    private Integer viabilidad;

    @OneToMany(mappedBy = "proyectos", fetch = FetchType.LAZY)
    private List<Estudiante> estudiantes = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejecucionproyecto_id")
    private EjecucionDelProyecto ejecucion;

    public ProyectoDeGrado() {}

    public Integer getProyectoId() { return proyectoId; }
    public void setProyectoId(Integer proyectoId) { this.proyectoId = proyectoId; }

    public String getAlcance() { return alcance; }
    public void setAlcance(String alcance) { this.alcance = alcance; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public String getPlanDeProyecto() { return planDeProyecto; }
    public void setPlanDeProyecto(String planDeProyecto) { this.planDeProyecto = planDeProyecto; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getCosto() { return costo; }
    public void setCosto(Integer costo) { this.costo = costo; }

    public String getTiempoDeRealizacion() { return tiempoDeRealizacion; }
    public void setTiempoDeRealizacion(String t) { this.tiempoDeRealizacion = t; }

    public Integer getViabilidad() { return viabilidad; }
    public void setViabilidad(Integer viabilidad) { this.viabilidad = viabilidad; }

    public List<Estudiante> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(List<Estudiante> estudiantes) { this.estudiantes = estudiantes; }

    public EjecucionDelProyecto getEjecucion() { return ejecucion; }
    public void setEjecucion(EjecucionDelProyecto ejecucion) { this.ejecucion = ejecucion; }

    public String getViabilidadLabel() {
        if (viabilidad == null) return "—";
        return switch (viabilidad) {
            case 1 -> "Alta";
            case 2 -> "Media";
            case 3 -> "Baja";
            default -> String.valueOf(viabilidad);
        };
    }
}
