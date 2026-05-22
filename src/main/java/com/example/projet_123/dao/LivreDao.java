package com.example.projet_123.dao;

import com.example.projet_123.model.Livre;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
@jakarta.transaction.Transactional
public class LivreDao {

    @jakarta.persistence.PersistenceContext
    private EntityManager em;

    public void save(Livre livre) {
        em.persist(livre);
    }

    public void update(Livre livre) {
        em.merge(livre);
    }

    public void delete(Livre livre) {
        Livre managedLivre = em.contains(livre) ? livre : em.merge(livre);
        em.remove(managedLivre);
    }

    public Livre findById(Long id) {
        return em.find(Livre.class, id);
    }

    public List<Livre> findAll() {
        TypedQuery<Livre> query = em.createQuery("SELECT l FROM Livre l", Livre.class);
        return query.getResultList();
    }

    public List<Livre> search(String keyword) {
        TypedQuery<Livre> query = em.createQuery(
                "SELECT l FROM Livre l WHERE LOWER(l.titre) LIKE :kw OR LOWER(l.auteur) LIKE :kw OR LOWER(l.categorie) LIKE :kw",
                Livre.class
        );
        query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }
}