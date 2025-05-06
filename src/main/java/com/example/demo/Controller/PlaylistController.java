package com.example.demo.controller;

import com.example.demo.model.Playlist;
import com.example.demo.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<List<Playlist>> getAll() {
        return ResponseEntity.ok(playlistService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getById(@PathVariable Long id) {
        Playlist playlist = playlistService.findById(id);
        return (playlist != null) ? ResponseEntity.ok(playlist) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Playlist> create(@RequestBody Playlist playlist) {
        return ResponseEntity.status(201).body(playlistService.save(playlist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> update(@PathVariable Long id, @RequestBody Playlist playlist) {
        Playlist existente = playlistService.findById(id);
        if (existente != null) {
            playlist.setId(id);
            return ResponseEntity.ok(playlistService.update(playlist));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Playlist existente = playlistService.findById(id);
        if (existente != null) {
            playlistService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Playlist>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(playlistService.buscarPorNombre(nombre));
    }
    @PostMapping("/{id}/agregar-cancion")
public ResponseEntity<Playlist> agregarCancion(
        @PathVariable Long id,
        @RequestParam String idCancion) {
    Playlist updated = playlistService.agregarCancion(id, idCancion);
    return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
}

@PostMapping("/{id}/eliminar-cancion")
public ResponseEntity<Playlist> eliminarCancion(
        @PathVariable Long id,
        @RequestParam String idCancion) {
    Playlist updated = playlistService.eliminarCancion(id, idCancion);
    return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
}

}
