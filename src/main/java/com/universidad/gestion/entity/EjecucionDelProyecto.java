package com.universidad.gestion.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ejecucion_del_proyecto")
public class EjecucionDelProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ejecucionproyecto_id")
    private Integer ejecucionproyectoId;

    @Column(name = "proyectoDeGradoTerminado")
    private String proyectoDeGradoTerminado;

    @Column(name = "recursosDisponibles")
    private String recursosDisponibles;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resultados_id")
    private Resultados resultados;

    @OneToMany(mappedBy = "ejecucionProyecto", fetch = FetchType.LAZY)
    private List<Estudiante> estudiantes = new ArrayList<>();

    @OneToMany(mappedBy = "ejecucionproyecto", fetch = FetchType.LAZY)
    private List<PersonalDocente> docentes = new ArrayList<>();

    public EjecucionDelProyecto() {}

    public Integer getEjecucionproyectoId() { return ejecucionproyectoId; }
    public void setEjecucionproyectoId(Integer id) { this.ejecucionproyectoId = id; }

    public String getProyectoDeGradoTerminado() { return proyectoDeGradoTerminado; }
    public void setProyectoDeGradoTerminado(String v) { this.proyectoDeGradoTerminado = v; }

    public String getRecursosDisponibles() { return recursosDisponibles; }
    public void setRecursosDisponibles(String v) { this.recursosDisponibles = v; }

    public Resultados getResultados() { return resultados; }
    public void setResultados(Resultados resultados) { this.resultados = resultados; }

    public List<Estudiante> getEstudiantes() { return estudiantes; }
    public List<PersonalDocente> getDocentes() { return docentes; }
}
