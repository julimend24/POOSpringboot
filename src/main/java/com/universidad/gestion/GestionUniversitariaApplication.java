package com.universidad.gestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación Spring Boot.
 * Migración del sistema de gestión universitaria (POO + JPA) a arquitectura web.
 */
@SpringBootApplication
public class GestionUniversitariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionUniversitariaApplication.class, args);
    }
}
