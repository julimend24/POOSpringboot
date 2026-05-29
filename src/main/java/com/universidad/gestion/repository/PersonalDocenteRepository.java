package com.universidad.gestion.repository;

import com.universidad.gestion.entity.PersonalDocente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonalDocenteRepository extends JpaRepository<PersonalDocente, Integer> {
    // Usa el nombre exacto del campo en la entidad: estudiante.estudianteId
    List<PersonalDocente> findByEstudiante_EstudianteId(Integer estudianteId);
}
