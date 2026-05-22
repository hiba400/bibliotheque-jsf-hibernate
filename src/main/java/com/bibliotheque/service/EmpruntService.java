package com.bibliotheque.service;

import com.bibliotheque.dao.EmpruntDAO;
import com.bibliotheque.dao.LivreDAO;
import com.bibliotheque.model.*;

import java.util.Date;
import java.util.List;

public class EmpruntService {

    private final EmpruntDAO empruntDAO = new EmpruntDAO();
    private final LivreDAO livreDAO = new LivreDAO();

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

    public Emprunt findById(Long id) {
        return empruntDAO.findById(id);
    }
}