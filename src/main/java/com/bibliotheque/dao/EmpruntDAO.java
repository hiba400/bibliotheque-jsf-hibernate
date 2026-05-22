package com.bibliotheque.dao;

import com.bibliotheque.config.HibernateUtil;
import com.bibliotheque.model.Emprunt;
import com.bibliotheque.model.Utilisateur;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmpruntDAO {

    public void save(Emprunt emprunt) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(emprunt);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void update(Emprunt emprunt) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(emprunt);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Emprunt findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Emprunt.class, id);
        }
    }

    public List<Emprunt> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Emprunt", Emprunt.class).list();
        }
    }

    public List<Emprunt> findByUtilisateur(Utilisateur utilisateur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Emprunt where utilisateur = :utilisateur",
                    Emprunt.class
            ).setParameter("utilisateur", utilisateur).list();
        }
    }
}
