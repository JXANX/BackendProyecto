package com.example.demo;

import com.example.demo.model.Cancion;
import com.example.demo.repository.CancionRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo")  // Esto asegura que escanee los repositorios
@EnableJpaRepositories(basePackages = "com.example.demo.repository") // Esto habilita los repositorios JPA
@EntityScan(basePackages = "com.example.demo.model") // Asegura que encuentre tus entidades
public class DemoProyectoApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(),entry.getValue()));
        SpringApplication.run(DemoProyectoApplication.class, args);
    }

    // Este método se ejecuta automáticamente al iniciar la app
    @Bean
    public CommandLineRunner cargarCancionesDesdeCarpeta(CancionRepository cancionRepository) {
        return args -> {
            Path carpetaCanciones = Paths.get("canciones"); // Carpeta local

            if (!Files.exists(carpetaCanciones)) {
                System.out.println("⚠️ La carpeta de canciones no existe.");
                return;
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(carpetaCanciones, "*.mp3")) {
                for (Path archivo : stream) {
                    String nombreArchivo = archivo.getFileName().toString();
                    String nombreSinExtension = nombreArchivo.replace(".mp3", "");

                    Cancion cancion = new Cancion(
                            nombreSinExtension, // título
                            "Desconocido",      // artista por defecto
                            "Género",           // género por defecto
                            "http://localhost:8080/canciones/" + nombreArchivo // URL para reproducir
                    );

                    // Solo guarda si no existe ya esa canción por nombre
                   if (cancionRepository.findByTituloContainingIgnoreCase(nombreSinExtension).isEmpty()) {
                        cancionRepository.save(cancion);
                        System.out.println("✅ Canción registrada: " + nombreArchivo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
