package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    // --- LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> optionalUsuario = usuarioService.getByEmail(loginRequest.getEmail());
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (passwordEncoder.matches(loginRequest.getContraseña(), usuario.getContraseña())) {
                return ResponseEntity.ok(new LoginResponse(usuario.getEmail(), usuario.getRol()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }

    // --- CREAR USUARIO ---
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // --- LISTAR TODOS LOS USUARIOS ---
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAll());
    }

    // --- OBTENER USUARIO POR ID ---
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        return usuarioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- ACTUALIZAR USUARIO ---
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return usuarioService.update(id, usuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- ELIMINAR USUARIO ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        boolean eliminado = usuarioService.delete(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // --- CLASE INTERNA: LoginRequest ---
    public static class LoginRequest {
        private String email;
        private String contraseña;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContraseña() {
            return contraseña;
        }

        public void setContraseña(String contraseña) {
            this.contraseña = contraseña;
        }
    }

    // --- CLASE INTERNA: LoginResponse ---
    public static class LoginResponse {
        private String email;
        private String rol;

        public LoginResponse(String email, String rol) {
            this.email = email;
            this.rol = rol;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }
    }
}
