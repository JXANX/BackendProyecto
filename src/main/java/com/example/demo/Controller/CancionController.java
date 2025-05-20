package com.example.demo.controller;

import com.example.demo.model.Cancion;
import com.example.demo.service.CancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/canciones")
public class CancionController {

    @Autowired
    private CancionService cancionService;

    @GetMapping
    public ResponseEntity<List<Cancion>> getAll() {
        return ResponseEntity.ok(cancionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cancion> getById(@PathVariable Long id) {
        Cancion cancion = cancionService.findById(id);
        return (cancion != null) ? ResponseEntity.ok(cancion) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cancion> create(@RequestBody Cancion cancion) {
        return ResponseEntity.status(201).body(cancionService.save(cancion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cancion> update(@PathVariable Long id, @RequestBody Cancion cancion) {
        Cancion existente = cancionService.findById(id);
        if (existente != null) {
            cancion.setId(id);
            return ResponseEntity.ok(cancionService.update(cancion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Cancion existente = cancionService.findById(id);
        if (existente != null) {
            cancionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cancion>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(cancionService.buscarPorTitulo(titulo));
    }

    // ✅ Evité conflicto con "/{id}" y "/{fileName:.+}" cambiando el endpoint del archivo a "/archivo/{fileName}"
    @GetMapping("/archivo/{fileName:.+}")
    public ResponseEntity<Resource> getCancion(@PathVariable String fileName) throws MalformedURLException {
        Path path = Paths.get("src/main/resources/static/canciones").resolve(fileName).normalize();
        Resource resource = new UrlResource(path.toUri());

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
