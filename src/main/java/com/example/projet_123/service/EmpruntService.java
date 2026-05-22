package com.example.projet_123.service;

import com.example.projet_123.dao.EmpruntDao;
import com.example.projet_123.dao.LivreDao;
import com.example.projet_123.model.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

@ApplicationScoped
@Transactional
public class EmpruntService {

    @Inject
    private EmpruntDao empruntDAO;
    @Inject
    private LivreDao livreDAO;

    public void demanderEmprunt(Utilisateur utilisateur, Livre livre) {
        if (!livre.isDisponible()) {
            throw new RuntimeException("Ce livre n'est pas disponible.");
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setUtilisateur(utilisateur);
        emprunt.setLivre(livre);
        emprunt.setDateDemande(new Date());
        emprunt.setStatut(StatutEmprunt.EN_ATTENTE);

        empruntDAO.save(emprunt);
    }

    public void validerEmprunt(Emprunt emprunt) {
        emprunt.setStatut(StatutEmprunt.VALIDE);
        emprunt.setDateValidation(new Date());

        Livre livre = emprunt.getLivre();
        livre.setDisponible(false);

        livreDAO.update(livre);
        empruntDAO.update(emprunt);
    }

    public void refuserEmprunt(Emprunt emprunt) {
        emprunt.setStatut(StatutEmprunt.REFUSE);
        empruntDAO.update(emprunt);
    }

    public void retournerLivre(Emprunt emprunt) {
        emprunt.setStatut(StatutEmprunt.RETOURNE);
        emprunt.setDateRetour(new Date());

        Livre livre = emprunt.getLivre();
        livre.setDisponible(true);

        livreDAO.update(livre);
        empruntDAO.update(emprunt);
    }

    public List<Emprunt> getAll() {
        return empruntDAO.findAll();
    }

    public List<Emprunt> getByUtilisateur(Utilisateur utilisateur) {
        return empruntDAO.findByUtilisateur(utilisateur);
    }

    public List<Emprunt> getByUtilisateurId(Long userId) {
        return empruntDAO.findByUtilisateurId(userId);
    }

    public Emprunt findById(Long id) {
        return empruntDAO.findById(id);
    }
}