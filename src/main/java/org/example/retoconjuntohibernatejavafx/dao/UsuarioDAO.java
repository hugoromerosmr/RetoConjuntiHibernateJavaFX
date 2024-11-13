package org.example.retoconjuntohibernatejavafx.dao;

import org.example.retoconjuntohibernatejavafx.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    private SessionFactory sessionFactory;
    public UsuarioDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    @Override
    public Usuario findById(Long id) {
        return null;
    }

    @Override
    public void save(Usuario usuario) {
        sessionFactory.inTransaction((session) -> session.persist(usuario));
    }

    @Override
    public void update(Usuario usuario) {

    }

    @Override
    public void delete(Usuario usuario) {

    }

    public Usuario findUserLogin (String email, String contraseña) {
        Session session = sessionFactory.openSession();
        Usuario usuario = null;
        try{
            Query<Usuario> query=session.createQuery("from Usuario where email = :email and contraseña = :contrasena", Usuario.class
            );
            query.setParameter("email", email.trim());
            query.setParameter("contrasena", contraseña);
            List<Usuario> resultList = query.list();

            if (!resultList.isEmpty()) {
                usuario = resultList.get(0);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

}
