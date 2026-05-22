package com.example.projet_123.dao;

import com.example.projet_123.model.Utilisateur;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
@jakarta.transaction.Transactional
public class UtilisateurDao {

    @jakarta.persistence.PersistenceContext
    private EntityManager em;

    public void save(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }

    public void update(Utilisateur utilisateur) {
        em.merge(utilisateur);
    }

    public void delete(Utilisateur utilisateur) {
        Utilisateur managedUtilisateur = em.contains(utilisateur) ? utilisateur : em.merge(utilisateur);
        em.remove(managedUtilisateur);
    }

    public Utilisateur findById(Long id) {
        return em.find(Utilisateur.class, id);
    }

    public List<Utilisateur> findAll() {
        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class);
        return query.getResultList();
    }

    public Utilisateur findByEmailAndPassword(String email, String password) {
        TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.email = :email AND u.password = :password",
                Utilisateur.class
        );
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Utilisateur> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public Utilisateur findByEmail(String email) {
        TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.email = :email",
                Utilisateur.class
        );
        query.setParameter("email", email);
        List<Utilisateur> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}