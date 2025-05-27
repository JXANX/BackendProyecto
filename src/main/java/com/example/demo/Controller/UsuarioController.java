package com.example.demo.controller;

import com.example.demo.Service.JwtService;
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

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> optionalUsuario = usuarioService.getByEmail(loginRequest.getEmail());
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (passwordEncoder.matches(loginRequest.getContraseña(), usuario.getContraseña())) {

                // Generar token JWT
                String token = jwtService.generateJwtToken(usuario);

                // Crear respuesta con token, email y datos del usuario (sin contraseña)
                return ResponseEntity.ok(new LoginResponse(usuario.getEmail(), token, usuario.getContraseña()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = jwtService.extractToken(authHeader);
        if (token != null && jwtService.validateJwtToken(token)) {
            String email = jwtService.getEmailFromToken(token);
            return ResponseEntity.ok(new TokenValidationResponse(email, "Token válido"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o no proporcionado");
        }
    }

    // --- CREAR USUARIO ---
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.registrarUsuario(usuario);
        if (creado == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }
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
        private String token;
        private String contraseña;

        public LoginResponse(String email, String token, String contraseña) {
            this.email = email;
            this.token = token;
            this.contraseña = contraseña;
        }

        public String getEmail() {
            return email;
        }

        public String getToken() {
            return token;
        }

        public String getContraseña() {
            return contraseña;
        }
    }

    public static class TokenValidationResponse {

        private String email;
        private String mensaje;

        public TokenValidationResponse(String email, String mensaje) {
            this.email = email;
            this.mensaje = mensaje;
        }

        public String getEmail() {
            return email;
        }

        public String getMensaje() {
            return mensaje;
        }
    }

}
