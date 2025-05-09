package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String contraseña;

    @Column(nullable = false, unique = true)
    private String usuario;

    // Constructor por defecto (requerido por JPA)
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String nombre, String correo, String telefono, String contraseña, String usuario) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.usuario = usuario;
    }

    // --- Getters ---

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    // --- Setters ---

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
