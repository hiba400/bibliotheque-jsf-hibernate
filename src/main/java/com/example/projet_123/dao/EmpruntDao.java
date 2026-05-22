package com.example.projet_123.dao;

import com.example.projet_123.model.Emprunt;
import com.example.projet_123.model.Livre;
import com.example.projet_123.model.Utilisateur;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
@jakarta.transaction.Transactional
public class EmpruntDao {

    @jakarta.persistence.PersistenceContext
    private EntityManager em;

    public void save(Emprunt emprunt) {
        em.persist(emprunt);
    }

    public void update(Emprunt emprunt) {
        em.merge(emprunt);
    }

    public Emprunt findById(Long id) {
        return em.find(Emprunt.class, id);
    }

    public List<Emprunt> findAll() {
        TypedQuery<Emprunt> query = em.createQuery("SELECT e FROM Emprunt e", Emprunt.class);
        return query.getResultList();
    }

    public List<Emprunt> findByUtilisateur(Utilisateur utilisateur) {
        TypedQuery<Emprunt> query = em.createQuery(
                "SELECT e FROM Emprunt e WHERE e.utilisateur = :utilisateur",
                Emprunt.class
        );
        query.setParameter("utilisateur", utilisateur);
        return query.getResultList();
    }

    public List<Emprunt> findByUtilisateurId(Long userId) {
        TypedQuery<Emprunt> query = em.createQuery(
                "SELECT e FROM Emprunt e WHERE e.utilisateur.id = :userId",
                Emprunt.class
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public long countByLivre(Livre livre) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(e) FROM Emprunt e WHERE e.livre = :livre", Long.class);
        query.setParameter("livre", livre);
        return query.getSingleResult();
    }
}
