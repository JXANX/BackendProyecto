package com.example.demo.repository;

import com.example.demo.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    private final List<Usuario> baseDeDatos = new ArrayList<>();

    public Usuario save(Usuario usuario) {
        baseDeDatos.add(usuario);
        return usuario;
    }

    public Usuario findById(String id) {
        for (Usuario usuario : baseDeDatos) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> findAll() {
        return new ArrayList<>(baseDeDatos);
    }

    public void deleteById(String id) {
        for (int i = 0; i < baseDeDatos.size(); i++) {
            if (baseDeDatos.get(i).getId().equals(id)) {
                baseDeDatos.remove(i);
                return;
            }
        }
    }

    public Usuario update(Usuario usuario) {
        for (int i = 0; i < baseDeDatos.size(); i++) {
            if (baseDeDatos.get(i).getId().equals(usuario.getId())) {
                baseDeDatos.set(i, usuario);
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> buscarPorFiltros(String nombre, String email) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : baseDeDatos) {
            boolean coincideNombre = (nombre == null
                    || usuario.getNombre().contains(nombre));
            boolean coincideEmail = (email == null
                    || usuario.getCorreo().contains(email));
            if (coincideNombre && coincideEmail) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }
}
