package dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import logica.Ingrediente;
import util.JPAUtil;
import java.util.List;

public class IngredienteDAO {

    public void guardar(Ingrediente ingrediente) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(ingrediente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al guardar el ingrediente: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Ingrediente buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Carga Ingrediente (i), la tabla intermedia (dr), y la Receta (r) asociada
            TypedQuery<Ingrediente> query = em.createQuery(
                    "SELECT i FROM Ingrediente i " +
                            "LEFT JOIN FETCH i.detallesRecetasDondeSeUsa dr " +
                            "LEFT JOIN FETCH dr.receta r " +
                            "WHERE i.id_ingrediente = :id",
                    Ingrediente.class
            );
            query.setParameter("id", id);

            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Ingrediente> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Ingrediente> query = em.createQuery("SELECT i FROM Ingrediente i ORDER BY i.nombre", Ingrediente.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void actualizar(Ingrediente ingrediente) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // El método merge es clave para la funcionalidad de editar.
            em.merge(ingrediente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al actualizar el ingrediente: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un ingrediente por su ID.
     */
    public void eliminar(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Ingrediente ingrediente = em.find(Ingrediente.class, id);

            if (ingrediente != null) {
                em.remove(ingrediente);
            }
            tx.commit();
        } catch (Exception e) {
            // Puede fallar si hay restricciones de clave foránea (el ingrediente se usa en una receta).
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al eliminar el ingrediente. Posiblemente se está usando en una receta: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}