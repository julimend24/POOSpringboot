package com.universidad.gestion.dto;

import jakarta.validation.constraints.*;

public class EstudianteDTO {

    private Integer estudianteId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String apellido;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener 7 u 8 dígitos numéricos")
    private String dni;

    @NotNull(message = "Debe indicar si es aspirante a grado")
    @Min(0) @Max(1)
    private Integer aspiranteAGrado = 0;

    private Integer proyectoId;

    public EstudianteDTO() {}

    public Integer getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Integer estudianteId) { this.estudianteId = estudianteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Integer getAspiranteAGrado() { return aspiranteAGrado; }
    public void setAspiranteAGrado(Integer aspiranteAGrado) { this.aspiranteAGrado = aspiranteAGrado; }

    public Integer getProyectoId() { return proyectoId; }
    public void setProyectoId(Integer proyectoId) { this.proyectoId = proyectoId; }
}
