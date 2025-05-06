package com.example.demo.repository;

import com.example.demo.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CancionRepository extends JpaRepository<Cancion, Long> {
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);
}
