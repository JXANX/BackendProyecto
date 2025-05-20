package com.example.demo.repository;

import com.example.demo.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CancionRepository extends JpaRepository<Cancion, Long> {
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT c FROM Cancion c WHERE c.id = :id")
    Cancion buscarPorId(@Param("id") Long id);
}
