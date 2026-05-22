package com.example.projet_123.service;

import com.example.projet_123.dao.UtilisateurDao;
import com.example.projet_123.model.Utilisateur;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

    @Inject
    private UtilisateurDao utilisateurDao;

    public Utilisateur login(String email, String password) {
        return utilisateurDao.findByEmailAndPassword(email, password);
    }

    public void register(Utilisateur utilisateur) {
        if (utilisateurDao.findByEmail(utilisateur.getEmail()) != null) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }
        utilisateurDao.save(utilisateur);
    }
}