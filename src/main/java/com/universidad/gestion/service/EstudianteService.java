package com.universidad.gestion.service;

import com.universidad.gestion.dto.EstudianteDTO;
import com.universidad.gestion.entity.Estudiante;
import com.universidad.gestion.entity.ProyectoDeGrado;
import com.universidad.gestion.repository.EstudianteRepository;
import com.universidad.gestion.repository.ProyectoDeGradoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final ProyectoDeGradoRepository proyectoRepository;

    public EstudianteService(EstudianteRepository estudianteRepository,
                             ProyectoDeGradoRepository proyectoRepository) {
        this.estudianteRepository = estudianteRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Transactional(readOnly = true)
    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAllWithRelaciones();
    }

    @Transactional(readOnly = true)
    public Optional<Estudiante> buscarPorId(Integer id) {
        return estudianteRepository.findByIdWithProyecto(id);
    }

    @Transactional(readOnly = true)
    public Optional<Estudiante> buscarConDocentes(Integer id) {
        return estudianteRepository.findByIdWithDocentes(id);
    }

    @Transactional(readOnly = true)
    public List<Estudiante> buscarPorNombre(String termino) {
        return estudianteRepository
            .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(termino, termino);
    }

    public Estudiante crear(EstudianteDTO dto) {
        estudianteRepository.findByDni(dto.getDni()).ifPresent(e -> {
            throw new IllegalArgumentException("Ya existe un estudiante con el DNI: " + dto.getDni());
        });

        Estudiante e = new Estudiante();
        e.setNombre(dto.getNombre().trim());
        e.setApellido(dto.getApellido().trim());
        e.setDni(dto.getDni().trim());
        e.setAspiranteAGrado(dto.getAspiranteAGrado());

        if (dto.getProyectoId() != null) {
            ProyectoDeGrado p = proyectoRepository.findById(dto.getProyectoId())
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
            e.setProyectos(p);
        }

        return estudianteRepository.save(e);
    }

    public Estudiante actualizar(Integer id, EstudianteDTO dto) {
        Estudiante e = estudianteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con ID: " + id));

        // Verificar DNI duplicado solo si cambió
        if (!e.getDni().equals(dto.getDni())) {
            estudianteRepository.findByDni(dto.getDni()).ifPresent(otro -> {
                if (!otro.getEstudianteId().equals(id))
                    throw new IllegalArgumentException("El DNI ya está registrado por otro estudiante.");
            });
        }

        e.setNombre(dto.getNombre().trim());
        e.setApellido(dto.getApellido().trim());
        e.setDni(dto.getDni().trim());
        e.setAspiranteAGrado(dto.getAspiranteAGrado());

        if (dto.getProyectoId() != null) {
            ProyectoDeGrado p = proyectoRepository.findById(dto.getProyectoId())
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
            e.setProyectos(p);
        } else {
            e.setProyectos(null);
        }

        return estudianteRepository.save(e);
    }

    public void eliminar(Integer id) {
        if (!estudianteRepository.existsById(id))
            throw new IllegalArgumentException("Estudiante no encontrado con ID: " + id);
        estudianteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long contarTotal() { return estudianteRepository.count(); }

    @Transactional(readOnly = true)
    public long contarAspirantesAGrado() {
        return estudianteRepository.findByAspiranteAGrado(1).size();
    }
}
