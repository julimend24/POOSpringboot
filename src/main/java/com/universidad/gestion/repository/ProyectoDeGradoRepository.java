package com.universidad.gestion.repository;

import com.universidad.gestion.entity.ProyectoDeGrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoDeGradoRepository extends JpaRepository<ProyectoDeGrado, Integer> {
    List<ProyectoDeGrado> findByTituloContainingIgnoreCase(String titulo);
    List<ProyectoDeGrado> findByViabilidad(Integer viabilidad);
}
