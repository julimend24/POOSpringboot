package com.universidad.gestion.service;

import com.universidad.gestion.entity.ProyectoDeGrado;
import com.universidad.gestion.repository.ProyectoDeGradoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de ProyectoDeGrado.
 * Equivalente a ProyectoDeGradoPersistencia del original,
 * enriquecido con lógica de negocio básica.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProyectoService {

    private final ProyectoDeGradoRepository proyectoRepository;

    @Transactional(readOnly = true)
    public List<ProyectoDeGrado> listarTodos() {
        return proyectoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ProyectoDeGrado> buscarPorId(Integer id) {
        return proyectoRepository.findById(id);
    }

    public ProyectoDeGrado guardar(ProyectoDeGrado proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void eliminar(Integer id) {
        proyectoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long contarTotal() {
        return proyectoRepository.count();
    }
}
