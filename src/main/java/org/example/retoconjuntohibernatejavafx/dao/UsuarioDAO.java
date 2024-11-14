package org.example.retoconjuntohibernatejavafx.dao;

import org.example.retoconjuntohibernatejavafx.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object (DAO) para la entidad `Usuario`.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos
 * y autenticación de usuarios.
 */
public class UsuarioDAO implements DAO<Usuario> {

    private SessionFactory sessionFactory;

    /**
     * Constructor que inicializa `UsuarioDAO` con una `SessionFactory` proporcionada.
     *
     * @param sessionFactory La `SessionFactory` utilizada para abrir sesiones de Hibernate.
     */
    public UsuarioDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Encuentra todos los usuarios en la base de datos.
     *
     * @return Una lista vacía (por implementar).
     */
    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    /**
     * Encuentra un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return `null` (por implementar).
     */
    @Override
    public Usuario findById(Long id) {
        return null;
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a guardar.
     */
    @Override
    public void save(Usuario usuario) {
        sessionFactory.inTransaction((session) -> session.persist(usuario));
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     * (Por implementar)
     *
     * @param usuario El usuario a actualizar.
     */
    @Override
    public void update(Usuario usuario) {
        // Método aún no implementado
    }

    /**
     * Elimina un usuario de la base de datos.
     * (Por implementar)
     *
     * @param usuario El usuario a eliminar.
     */
    @Override
    public void delete(Usuario usuario) {
        // Método aún no implementado
    }

    /**
     * Encuentra un usuario por su correo electrónico y contraseña.
     * Permite autenticar un usuario en el sistema.
     *
     * @param email       El correo electrónico del usuario.
     * @param contraseña  La contraseña del usuario.
     * @return El usuario si se encuentra; `null` en caso contrario.
     */
    public Usuario findUserLogin(String email, String contraseña) {
        Session session = sessionFactory.openSession();
        Usuario usuario = null;
        try {
            Query<Usuario> query = session.createQuery("from Usuario where email = :email and contraseña = :contrasena", Usuario.class);
            query.setParameter("email", email.trim());
            query.setParameter("contrasena", contraseña);
            List<Usuario> resultList = query.list();

            if (!resultList.isEmpty()) {
                usuario = resultList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
