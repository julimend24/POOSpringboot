package com.universidad.gestion.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "universidad")
public class Universidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "universidad_id")
    private Integer universidadId;

    @Column(name = "cunaDelConocimiento")
    private String cunaDelConocimiento;

    @Column(name = "diferentesCarreras")
    private String diferentesCarreras;

    @Column(name = "privada")
    private String privada;

    @Column(name = "publica")
    private String publica;

    @ManyToMany(mappedBy = "universidades", fetch = FetchType.LAZY)
    private List<Estudiante> estudiantes = new ArrayList<>();

    public Universidad() {}

    public Integer getUniversidadId() { return universidadId; }
    public void setUniversidadId(Integer id) { this.universidadId = id; }

    public String getCunaDelConocimiento() { return cunaDelConocimiento; }
    public void setCunaDelConocimiento(String v) { this.cunaDelConocimiento = v; }

    public String getDiferentesCarreras() { return diferentesCarreras; }
    public void setDiferentesCarreras(String v) { this.diferentesCarreras = v; }

    public String getPrivada() { return privada; }
    public void setPrivada(String v) { this.privada = v; }

    public String getPublica() { return publica; }
    public void setPublica(String v) { this.publica = v; }

    public List<Estudiante> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(List<Estudiante> v) { this.estudiantes = v; }
}
