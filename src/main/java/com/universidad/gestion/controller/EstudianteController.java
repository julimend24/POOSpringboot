package com.universidad.gestion.controller;

import com.universidad.gestion.dto.EstudianteDTO;
import com.universidad.gestion.entity.Estudiante;
import com.universidad.gestion.service.EstudianteService;
import com.universidad.gestion.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final ProyectoService proyectoService;

    public EstudianteController(EstudianteService estudianteService, ProyectoService proyectoService) {
        this.estudianteService = estudianteService;
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        List<Estudiante> estudiantes = (buscar != null && !buscar.isBlank())
            ? estudianteService.buscarPorNombre(buscar.trim())
            : estudianteService.listarTodos();
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("buscar", buscar);
        return "estudiantes/listado";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        return estudianteService.buscarConDocentes(id)
            .map(e -> { model.addAttribute("estudiante", e); return "estudiantes/detalle"; })
            .orElseGet(() -> { ra.addFlashAttribute("error", "Estudiante no encontrado."); return "redirect:/estudiantes"; });
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("estudianteDTO", new EstudianteDTO());
        model.addAttribute("proyectos", proyectoService.listarTodos());
        model.addAttribute("modo", "nuevo");
        return "estudiantes/formulario";
    }

    @PostMapping("/nuevo")
    public String guardarNuevo(@Valid @ModelAttribute("estudianteDTO") EstudianteDTO dto,
                               BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("proyectos", proyectoService.listarTodos());
            model.addAttribute("modo", "nuevo");
            return "estudiantes/formulario";
        }
        try {
            Estudiante creado = estudianteService.crear(dto);
            ra.addFlashAttribute("exito", "Estudiante " + creado.getNombreCompleto() + " registrado con éxito.");
            return "redirect:/estudiantes/" + creado.getEstudianteId();
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorServicio", ex.getMessage());
            model.addAttribute("proyectos", proyectoService.listarTodos());
            model.addAttribute("modo", "nuevo");
            return "estudiantes/formulario";
        }
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        return estudianteService.buscarPorId(id).map(e -> {
            EstudianteDTO dto = new EstudianteDTO();
            dto.setEstudianteId(e.getEstudianteId());
            dto.setNombre(e.getNombre());
            dto.setApellido(e.getApellido());
            dto.setDni(e.getDni());
            dto.setAspiranteAGrado(e.getAspiranteAGrado());
            if (e.getProyectos() != null) dto.setProyectoId(e.getProyectos().getProyectoId());
            model.addAttribute("estudianteDTO", dto);
            model.addAttribute("proyectos", proyectoService.listarTodos());
            model.addAttribute("modo", "editar");
            return "estudiantes/formulario";
        }).orElseGet(() -> { ra.addFlashAttribute("error", "Estudiante no encontrado."); return "redirect:/estudiantes"; });
    }

    @PostMapping("/{id}/editar")
    public String guardarEdicion(@PathVariable Integer id,
                                 @Valid @ModelAttribute("estudianteDTO") EstudianteDTO dto,
                                 BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("proyectos", proyectoService.listarTodos());
            model.addAttribute("modo", "editar");
            return "estudiantes/formulario";
        }
        try {
            estudianteService.actualizar(id, dto);
            ra.addFlashAttribute("exito", "Estudiante actualizado correctamente.");
            return "redirect:/estudiantes/" + id;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorServicio", ex.getMessage());
            model.addAttribute("proyectos", proyectoService.listarTodos());
            model.addAttribute("modo", "editar");
            return "estudiantes/formulario";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            estudianteService.eliminar(id);
            ra.addFlashAttribute("exito", "Estudiante eliminado correctamente.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/estudiantes";
    }
}
