package com.example.demo.service;

import com.example.demo.model.Playlist;
import com.example.demo.repository.PlaylistRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    public Playlist findById(Long id) {
        return playlistRepository.findById(id).orElse(null);
    }

    public Playlist save(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist update(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public void deleteById(Long id) {
        playlistRepository.deleteById(id);
    }

    public List<Playlist> buscarPorNombre(String nombre) {
        return playlistRepository.findByNombreContainingIgnoreCase(nombre);
    }
    public Playlist agregarCancion(Long playlistId, String idCancion) {
    Playlist playlist = findById(playlistId);
    if (playlist == null) return null;

    String canciones = playlist.getCanciones();
    if (canciones == null || canciones.isEmpty()) {
        playlist.setCanciones(idCancion);
    } else {
        // Evitar duplicados
        List<String> lista = new ArrayList<>(List.of(canciones.split(",")));
        if (!lista.contains(idCancion)) {
            lista.add(idCancion);
            playlist.setCanciones(String.join(",", lista));
        }
    }
    return playlistRepository.save(playlist);
}

public Playlist eliminarCancion(Long playlistId, String idCancion) {
    Playlist playlist = findById(playlistId);
    if (playlist == null) return null;

    String canciones = playlist.getCanciones();
    if (canciones != null && !canciones.isEmpty()) {
        List<String> lista = new ArrayList<>(List.of(canciones.split(",")));
        lista.removeIf(c -> c.equals(idCancion));
        playlist.setCanciones(String.join(",", lista));
        return playlistRepository.save(playlist);
    }
    return playlist;
}

}
