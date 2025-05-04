package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario nuevoUsuario) {
        Optional<Usuario> existente = usuarioRepository.findByUsuario(nuevoUsuario.getUsuario());
        if (existente.isPresent()) {
            return null; // Usuario ya existe
        }
        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario login(String usuario, String contraseña) {
        return usuarioRepository.findByUsuarioAndContraseña(usuario, contraseña).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> buscarPorFiltros(String nombre, String correo) {
        return usuarioRepository.findByNombreContainingAndCorreoContaining(nombre, correo);
    }
}
