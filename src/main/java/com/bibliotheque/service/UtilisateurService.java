package com.bibliotheque.service;

import com.bibliotheque.dao.UtilisateurDAO;
import com.bibliotheque.model.Utilisateur;

import java.util.List;

public class UtilisateurService {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public void ajouter(Utilisateur utilisateur) {
        utilisateurDAO.save(utilisateur);
    }

    public void modifier(Utilisateur utilisateur) {
        utilisateurDAO.update(utilisateur);
    }

    public void supprimer(Utilisateur utilisateur) {
        utilisateurDAO.delete(utilisateur);
    }

    public List<Utilisateur> getAll() {
        return utilisateurDAO.findAll();
    }

    public Utilisateur findById(Long id) {
        return utilisateurDAO.findById(id);
    }
}