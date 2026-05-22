package com.example.projet_123.config;

import org.hibernate.Session;

public class TestHibernate {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Connexion Hibernate OK");
        session.close();
    }
}