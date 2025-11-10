package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import logica.Dificultad;
import logica.Modalidad;
import logica.Receta;
import util.JPAUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecetaDAO {
    //*******GUARDAR*******
    public void guardar(Receta receta) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(receta);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al guardar la receta: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    //*******BUSCAR POR ID*******
    public Receta buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Consulta para carga ansiosa de la receta y sus detalles
            TypedQuery<Receta> query = em.createQuery(
                    "SELECT r FROM Receta r LEFT JOIN FETCH r.detallesRecetas dr LEFT JOIN FETCH dr.ingrediente i WHERE r.id_receta = :id",
                    Receta.class
            );
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    //*******LISTAR TODAS LAS RECETAS*******
    public List<Receta> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Receta> query = em.createQuery("SELECT r FROM Receta r ORDER BY r.nombre", Receta.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    //*******BUSCAR CON FILTROS*******
    public List<Receta> buscarPorFiltros(String modalidad, String dificultad) {
        EntityManager em = JPAUtil.getEntityManager();

        // 1. Iniciar la consulta base
        StringBuilder jpql = new StringBuilder("SELECT r FROM Receta r WHERE 1=1");
        Map<String, Object> parametros = new HashMap<>();

        // 2. Añadir filtros dinámicamente
        if (modalidad != null && !modalidad.isEmpty()) {
            jpql.append(" AND r.modalidad = :modalidad");
            parametros.put("modalidad", Modalidad.valueOf(modalidad));
        }

        if (dificultad != null && !dificultad.isEmpty()) {
            jpql.append(" AND r.dificultad = :dificultad");
            parametros.put("dificultad", Dificultad.valueOf(dificultad));
        }

        // 3. Ordenar
        jpql.append(" ORDER BY r.nombre");

        try {
            // 4. Crear la consulta y asignar parámetros
            TypedQuery<Receta> query = em.createQuery(jpql.toString(), Receta.class);

            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }
    //*******ACTUALIZAR*******
    public void actualizar(Receta receta) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // El método merge se encarga de tomar el objeto y actualizar su estado en la DB.
            em.merge(receta);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al actualizar la receta: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    //*******ELIMINAR RECETA POR ID*******
    public void eliminar(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Receta receta = em.find(Receta.class, id);

            if (receta != null) {
                em.remove(receta);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al eliminar la receta: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}