package org.example.retoconjuntohibernatejavafx.dao;

import org.example.retoconjuntohibernatejavafx.utils.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object (DAO) para la entidad `Pelicula`.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos.
 */
public class PeliculaDAO {
    private SessionFactory sessionFactory;

    /**
     * Constructor sin argumentos.
     */
    public PeliculaDAO() {}

    /**
     * Constructor que inicializa `PeliculaDAO` con una `SessionFactory` proporcionada.
     *
     * @param sessionFactory La `SessionFactory` utilizada para abrir sesiones de Hibernate.
     */
    public PeliculaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Encuentra todas las películas en la base de datos.
     *
     * @return Una lista de todas las películas.
     * @throws RuntimeException Si ocurre un error al obtener la lista de películas.
     */
    public List<Pelicula> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Pelicula> query = session.createQuery("from Pelicula", Pelicula.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la lista de películas", e);
        }
    }

    /**
     * Encuentra una película por su ID.
     *
     * @param id El ID de la película.
     * @return La película correspondiente al ID, o `null` si no se encuentra.
     */
    public Pelicula findById(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Pelicula.class, id);
        }
    }

    /**
     * Guarda una nueva película en la base de datos.
     *
     * @param pelicula La película a guardar.
     */
    public void save(Pelicula pelicula) {
        sessionFactory.inTransaction((session) -> session.persist(pelicula));
    }

    /**
     * Elimina una película de la base de datos.
     *
     * @param pelicula La película a eliminar.
     */
    public void delete(Pelicula pelicula) {
        sessionFactory.inTransaction((session) -> session.remove(pelicula));
    }
}
