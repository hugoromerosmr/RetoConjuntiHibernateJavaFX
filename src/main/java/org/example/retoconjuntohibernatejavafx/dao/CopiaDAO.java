package org.example.retoconjuntohibernatejavafx.dao;

import org.example.retoconjuntohibernatejavafx.models.Copia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object (DAO) para la entidad `Copia`.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos.
 */
public class CopiaDAO implements DAO<Copia> {

    private SessionFactory sessionFactory;

    /**
     * Constructor que inicializa `CopiaDAO` con una `SessionFactory` proporcionada.
     *
     * @param sessionFactory La `SessionFactory` utilizada para abrir sesiones de Hibernate.
     */
    public CopiaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Constructor sin argumentos.
     */
    public CopiaDAO() {}

    /**
     * Encuentra todas las copias.
     *
     * @return Una lista vacía (por implementar).
     */
    @Override
    public List<Copia> findAll() {
        return List.of();
    }

    /**
     * Encuentra una copia por su ID.
     *
     * @param id El ID de la copia.
     * @return `null` (por implementar).
     */
    @Override
    public Copia findById(Long id) {
        return null;
    }

    /**
     * Guarda una nueva copia en la base de datos.
     *
     * @param copia La copia a guardar.
     */
    @Override
    public void save(Copia copia) {
        sessionFactory.inTransaction((session) -> session.persist(copia));
    }

    /**
     * Actualiza una copia existente en la base de datos.
     *
     * @param copia La copia a actualizar.
     */
    public void update(Copia copia) {
        sessionFactory.inTransaction((session) -> session.merge(copia));
    }

    /**
     * Elimina una copia de la base de datos.
     *
     * @param copia La copia a eliminar.
     */
    public void delete(Copia copia) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(session.merge(copia));
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Encuentra todas las copias asociadas a un usuario específico.
     *
     * @param idUsuario El ID del usuario.
     * @return Una lista de copias pertenecientes al usuario.
     */
    public List<Copia> findByUser(Long idUsuario) {
        Session session = sessionFactory.openSession();
        Query<Copia> query = session.createQuery("from Copia where usuario.id=:idUsuario", Copia.class);
        query.setParameter("idUsuario", idUsuario);
        return query.list();
    }
}
