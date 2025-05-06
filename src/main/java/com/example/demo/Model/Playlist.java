package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    // Guardaremos los IDs o títulos de las canciones como texto plano, separados por comas
    @Column(length = 1000)
    private String canciones; // Ejemplo: "1,5,7" o "Imagine,Let it Be"

    public Playlist() {}

    public Playlist(String nombre, String descripcion, String canciones) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.canciones = canciones;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCanciones() { return canciones; }
    public void setCanciones(String canciones) { this.canciones = canciones; }
}
