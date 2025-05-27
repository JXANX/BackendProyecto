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
@ComponentScan(basePackages = "com.example.demo")
@EnableJpaRepositories(basePackages = "com.example.demo.repository")
@EntityScan(basePackages = "com.example.demo.model")
public class DemoProyectoApplication {

   public static void main(String[] args) {
		// Configura Dotenv para que ignore si el archivo .env no se encuentra
		Dotenv dotenv = Dotenv.configure()
                            .ignoreIfMissing() // Esta es la clave
                            .load();

		// Si dotenv cargó algo (es decir, .env existía), establece las propiedades
		if (dotenv != null) {
			dotenv.entries().forEach(entry -> {
				// Opcional: podrías verificar si la propiedad ya existe en el sistema
				// para no sobrescribir las variables de entorno de Render.
				// Sin embargo, Spring Boot tiene su propio orden de precedencia,
				// y las variables de entorno del sistema suelen tener mayor prioridad.
				// Por seguridad, puedes añadir una comprobación:
				if (System.getProperty(entry.getKey()) == null && System.getenv(entry.getKey()) == null) {
					System.setProperty(entry.getKey(), entry.getValue());
				}
				// O, si quieres que .env SOBRESCRIBA las de Render (NO RECOMENDADO para este caso):
				// System.setProperty(entry.getKey(), entry.getValue());
			});
		}

		SpringApplication.run(DemoProyectoApplication.class, args);
	}

    @Bean
    public CommandLineRunner cargarCancionesDesdeCarpeta(CancionRepository cancionRepository) {
        return args -> {
            Path carpetaCanciones = Paths.get("canciones");

            if (!Files.exists(carpetaCanciones)) {
                System.out.println("⚠️ La carpeta de canciones no existe.");
                return;
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(carpetaCanciones, "*.mp3")) {
                for (Path archivo : stream) {
                    String nombreArchivo = archivo.getFileName().toString();
                    String nombreSinExtension = nombreArchivo.replace(".mp3", "");

                    Cancion cancion = new Cancion(
                            nombreSinExtension,
                            "Desconocido",
                            "Género",
                            "http://localhost:8080/canciones/" + nombreArchivo
                    );

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
