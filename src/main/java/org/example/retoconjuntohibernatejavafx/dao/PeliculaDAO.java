package org.example.retoconjuntohibernatejavafx.dao;

import org.example.retoconjuntohibernatejavafx.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.models.Copia;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;
import org.example.retoconjuntohibernatejavafx.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Currency;
import java.util.List;

public class PeliculaDAO {
    private SessionFactory sessionFactory;

    public PeliculaDAO() {
    }

    public PeliculaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Pelicula> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Pelicula> query = session.createQuery("from Pelicula", Pelicula.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la lista de películas", e);
        }
    }
    public Pelicula findById(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Pelicula pelicula = session.get(Pelicula.class, id);
            return pelicula;
        }
    }
    public void save(Pelicula pelicula) {
        sessionFactory.inTransaction((session) -> session.persist(pelicula));
    }
    public void delete(Pelicula pelicula) {
        sessionFactory.inTransaction((session) -> session.remove(pelicula));
    }

}
