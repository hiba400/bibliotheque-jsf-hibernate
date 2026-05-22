package com.bibliotheque.service;

import com.bibliotheque.dao.UtilisateurDAO;
import com.bibliotheque.model.Utilisateur;

public class AuthService {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Utilisateur login(String email, String password) {
        return utilisateurDAO.findByEmailAndPassword(email, password);
    }
}