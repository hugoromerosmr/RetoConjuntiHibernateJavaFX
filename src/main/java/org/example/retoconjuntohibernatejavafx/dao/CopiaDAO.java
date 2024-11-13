package org.example.retoconjuntohibernatejavafx.dao;

import org.example.retoconjuntohibernatejavafx.models.Copia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CopiaDAO implements DAO<Copia> {

    private SessionFactory sessionFactory;

    public CopiaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public CopiaDAO(){}
    @Override
    public List<Copia> findAll() {
        return List.of();
    }

    @Override
    public Copia findById(Long id) {
        return null;
    }

    @Override
    public void save(Copia copia) {
        sessionFactory.inTransaction((session) -> session.persist(copia));
    }


    public void update(Copia copia) {
        sessionFactory.inTransaction((session) -> session.merge(copia));
    }

    public void delete(Copia copia) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(session.merge(copia));
        session.getTransaction().commit();
        session.close();
    }

    public List<Copia> findByUser(Long idUsuario) {
        Session session=sessionFactory.openSession();
        Query<Copia> query=session.createQuery("from Copia where usuario.id=:idUsuario", Copia.class);
        query.setParameter("idUsuario", idUsuario);
        return query.list();
    }
}
