package com.universidad.gestion.repository;

import com.universidad.gestion.entity.Estudiante;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    List<Estudiante> findByAspiranteAGrado(Integer aspiranteAGrado);

    Optional<Estudiante> findByDni(String dni);

    List<Estudiante> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre, String apellido);

    /** Listado: carga proyectos y ejecucionProyecto para evitar lazy en las vistas */
    @EntityGraph(attributePaths = {"proyectos", "ejecucionProyecto"})
    @Query("SELECT e FROM Estudiante e")
    List<Estudiante> findAllWithRelaciones();

    /** Detalle: carga proyectos, ejecucionProyecto y docentes de una sola vez */
    @EntityGraph(attributePaths = {"proyectos", "ejecucionProyecto", "docentes"})
    @Query("SELECT e FROM Estudiante e WHERE e.estudianteId = :id")
    Optional<Estudiante> findByIdWithDocentes(@Param("id") Integer id);

    /** Editar: solo necesita proyectos */
    @Query("SELECT e FROM Estudiante e LEFT JOIN FETCH e.proyectos WHERE e.estudianteId = :id")
    Optional<Estudiante> findByIdWithProyecto(@Param("id") Integer id);
}
