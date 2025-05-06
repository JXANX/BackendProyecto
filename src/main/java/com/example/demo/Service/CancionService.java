package com.example.demo.service;

import com.example.demo.model.Cancion;
import com.example.demo.repository.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancionService {

    @Autowired
    private CancionRepository cancionRepository;

    public List<Cancion> findAll() {
        return cancionRepository.findAll();
    }

    public Cancion findById(Long id) {
        return cancionRepository.findById(id).orElse(null);
    }

    public Cancion save(Cancion cancion) {
        return cancionRepository.save(cancion);
    }

    public Cancion update(Cancion cancion) {
        return cancionRepository.save(cancion);
    }

    public void deleteById(Long id) {
        cancionRepository.deleteById(id);
    }

    public List<Cancion> buscarPorTitulo(String titulo) {
        return cancionRepository.findByTituloContainingIgnoreCase(titulo);
    }
}
