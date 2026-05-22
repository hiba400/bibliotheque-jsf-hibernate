package com.example.projet_123.service;

import com.example.projet_123.dao.UtilisateurDao;
import com.example.projet_123.model.Utilisateur;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class UtilisateurService {

    @Inject
    private UtilisateurDao utilisateurDAO;

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