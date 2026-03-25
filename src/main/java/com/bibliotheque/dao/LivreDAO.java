package com.bibliotheque.dao;

import com.bibliotheque.config.HibernateUtil;
import com.bibliotheque.model.Livre;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LivreDAO {

    public void save(Livre livre) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(livre);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void update(Livre livre) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(livre);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Livre livre) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(session.contains(livre) ? livre : session.merge(livre));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Livre findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Livre.class, id);
        }
    }

    public List<Livre> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Livre", Livre.class).list();
        }
    }

    public List<Livre> search(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Livre where lower(titre) like :kw or lower(auteur) like :kw or lower(categorie) like :kw",
                            Livre.class
                    ).setParameter("kw", "%" + keyword.toLowerCase() + "%")
                    .list();
        }
    }
}