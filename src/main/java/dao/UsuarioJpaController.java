/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import util.DESCipher;
import util.MD5;


/**
 *
 * @author harol
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_BCrypt01_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UsuarioJpaController() {
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getCodiUsua()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getCodiUsua();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCodiUsua();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Usuario validarUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Usuario.validar");
            query.setParameter("logiUsua", usuario.getLogiUsua());
            query.setParameter("passUsua", usuario.getPassUsua());
            return (Usuario) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        } finally {
            em.close();
        }
    }

    public String cambiarClave(Usuario u, String nuevaClave) {
        EntityManager em = getEntityManager();
        try {
            Usuario usuario = validarUsuario(u);
            if (usuario != null) { // Verifica que el usuario exista
                if (usuario.getPassUsua().equals(u.getPassUsua())) {
                    usuario.setPassUsua(nuevaClave);
                    edit(usuario);
                    return "Clave cambiada";
                } else {
                    return "Clave actual no válida";
                }
            } else {
                return "Usuario no encontrado"; // Manejo de usuario no encontrado
            }
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }



    public static void main(String[] args) throws Exception {
        UsuarioJpaController ujc = new UsuarioJpaController();

        // Clave maestra para DES (debe tener exactamente 8 caracteres)
        String claveDES = "12345678";

        // Contraseña ingresada
        String inputPassword = "1234";

        // Cifrar la contraseña ingresada (opcional)
        String passCifrada = DESCipher.cifrar(inputPassword, claveDES);
        System.out.println("Contraseña cifrada con DES: " + passCifrada);
        System.out.println("-----------------------------------------");
        String passDescifrada = DESCipher.descifrar(passCifrada, claveDES);
        System.out.println("Contraseña descifrada con DES: " + passDescifrada);
        System.out.println("-----------------------------------------");
        String resultado = MD5.getMD5(passDescifrada);
        System.out.println("Contraseña MD5: " + resultado);
        System.out.println("-----------------------------------------");
        // Supongamos que el hash BCrypt está guardado en la base de datos para el usuario "kike"
        Usuario usuario; // usar en producción: passCifrada si se guarda cifrada
        usuario = ujc.validarUsuario(new Usuario("test", "81dc9bdb52d04dc20036dbd8313ed055"));

        if (usuario != null) {
            System.out.println("Existe");
        } else {
            System.out.println("No existe");
        }
    }

}
