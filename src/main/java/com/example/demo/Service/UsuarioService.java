package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrar nuevo usuario si no existe
    public Usuario registrarUsuario(Usuario nuevoUsuario) {
        Optional<Usuario> existente = usuarioRepository.findByEmail(nuevoUsuario.getEmail());
        if (existente.isPresent()) {
            return null; // Usuario ya existe
        }
        nuevoUsuario.setContraseña(passwordEncoder.encode(nuevoUsuario.getContraseña()));
        return usuarioRepository.save(nuevoUsuario);
    }

    // Login (retorna null si no coincide)
    public Usuario login(String email, String rawPassword) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(rawPassword, usuario.getContraseña())) {
                return usuario;
            }
        }
        return null;
    }

    // Método para crear un nuevo usuario
    public Usuario save(Usuario usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<Usuario> getById(Integer id) {
        return usuarioRepository.findById(id);
    }

    // Buscar usuario por email
    public Optional<Usuario> getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Actualizar usuario por ID
    public Optional<Usuario> update(Integer id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
            return Optional.of(usuarioRepository.save(usuario));
        }
        return Optional.empty();
    }

    // Eliminar usuario por ID
    public boolean delete(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar usuarios por filtros (nombre y correo)
    public List<Usuario> buscarPorFiltros(String nombre, String correo) {
        return usuarioRepository.findByNombreContainingAndCorreoContaining(nombre, correo);
    }

    // Verificar existencia de un usuario por email
    public boolean existByEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    // Verificar si un usuario existe por ID
    public boolean existById(Integer id) {
        return usuarioRepository.existsById(id);
    }
}
