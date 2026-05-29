// ============================================================
// DashboardController.java
// Equivalente al CtrlPrincipal del original.
// Muestra la pantalla principal (landing) con estadísticas.
// ============================================================
package com.universidad.gestion.controller;

import com.universidad.gestion.service.EstudianteService;
import com.universidad.gestion.service.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final EstudianteService estudianteService;
    private final ProyectoService proyectoService;

    /**
     * Redirige "/" al dashboard principal.
     * Equivalente al comportamiento de CtrlPrincipal.iniciar()
     * que mostraba la vista Principal.java (JFrame).
     */
    @GetMapping("/")
    public String raiz() {
        return "redirect:/dashboard";
    }

    /**
     * Dashboard principal.
     * Carga estadísticas y los últimos estudiantes registrados
     * para mostrar en el landing.
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalEstudiantes", estudianteService.contarTotal());
        model.addAttribute("totalProyectos", proyectoService.contarTotal());
        model.addAttribute("totalAspirantes", estudianteService.contarAspirantesAGrado());
        model.addAttribute("ultimosEstudiantes", estudianteService.listarTodos()
                .stream().limit(5).toList());
        return "principal"; // → templates/principal.html
    }
}
