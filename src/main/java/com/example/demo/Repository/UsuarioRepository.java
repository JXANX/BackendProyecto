package com.example.demo.repository;

import com.example.demo.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Usuario create(Usuario usuario) {
        entityManager.persist(usuario);
        return usuario;
    }

    @Transactional
    public List<Usuario> findAll() {
        Query query = entityManager.createNativeQuery("SELECT * FROM usuarios", Usuario.class);
        return query.getResultList();
    }

    @Transactional
    public Optional<Usuario> findById(Integer id) {
        Query query = entityManager.createNativeQuery("SELECT * FROM usuarios WHERE id = :id", Usuario.class);
        query.setParameter("id", id);
        try {
            Usuario usuario = (Usuario) query.getSingleResult();
            return Optional.of(usuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Usuario> update(Integer id, Usuario usuario) {
        Query query = entityManager.createNativeQuery(
            "UPDATE usuarios SET nombres = :nombres, apellidos = :apellidos, contraseña = :contraseña, email = :email, rol = :rol WHERE id = :id"
        );
        query.setParameter("nombres", usuario.getNombres());
        query.setParameter("apellidos", usuario.getApellidos());
        query.setParameter("contraseña", usuario.getContraseña());
        query.setParameter("email", usuario.getEmail());
        query.setParameter("rol", usuario.getRol());
        query.setParameter("id", id);
        int updated = query.executeUpdate();
        if (updated > 0) {
            return findById(id);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean delete(Integer id) {
        Query query = entityManager.createNativeQuery("DELETE FROM usuarios WHERE id = :id");
        query.setParameter("id", id);
        int deleted = query.executeUpdate();
        return deleted > 0;
    }

    @Transactional
    public Optional<Usuario> findByUsuario(String usuario) {
        Query query = entityManager.createNativeQuery("SELECT * FROM usuarios WHERE usuario = :usuario", Usuario.class);
        query.setParameter("usuario", usuario);
        try {
            return Optional.of((Usuario) query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Usuario> findByUsuarioAndContraseña(String usuario, String contraseña) {
        Query query = entityManager.createNativeQuery("SELECT * FROM usuarios WHERE usuario = :usuario AND contraseña = :contraseña", Usuario.class);
        query.setParameter("usuario", usuario);
        query.setParameter("contraseña", contraseña);
        try {
            return Optional.of((Usuario) query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public List<Usuario> findByNombreContainingAndCorreoContaining(String nombre, String correo) {
        Query query = entityManager.createNativeQuery(
            "SELECT * FROM usuarios WHERE nombre LIKE :nombre AND correo LIKE :correo", Usuario.class);
        query.setParameter("nombre", "%" + nombre + "%");
        query.setParameter("correo", "%" + correo + "%");
        return query.getResultList();
    }
    @Transactional
public Optional<Usuario> findByEmail(String email) {
    Query query = entityManager.createNativeQuery("SELECT * FROM usuarios WHERE email = :email", Usuario.class);
    query.setParameter("email", email);
    try {
        return Optional.of((Usuario) query.getSingleResult());
    } catch (Exception e) {
        return Optional.empty();
    }
}

@Transactional
public boolean existsById(Integer id) {
    Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM usuarios WHERE id = :id");
    query.setParameter("id", id);
    Number count = (Number) query.getSingleResult();
    return count.intValue() > 0;
}

@Transactional
public Usuario save(Usuario usuario) {
    if (usuario.getId() == null || !existsById(usuario.getId())) {
        entityManager.persist(usuario);
        return usuario;
    } else {
        return entityManager.merge(usuario);
    }
}

@Transactional
public void deleteById(Integer id) {
    Query query = entityManager.createNativeQuery("DELETE FROM usuarios WHERE id = :id");
    query.setParameter("id", id);
    query.executeUpdate();
}

}
