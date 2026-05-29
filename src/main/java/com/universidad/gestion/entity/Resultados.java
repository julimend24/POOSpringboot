package com.universidad.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "resultados")
public class Resultados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultados_id")
    private Integer resultadosId;

    @Column(name = "implementacionFinalizada")
    private String implementacionFinalizada;

    public Resultados() {}

    public Integer getResultadosId() { return resultadosId; }
    public void setResultadosId(Integer id) { this.resultadosId = id; }

    public String getImplementacionFinalizada() { return implementacionFinalizada; }
    public void setImplementacionFinalizada(String v) { this.implementacionFinalizada = v; }
}
