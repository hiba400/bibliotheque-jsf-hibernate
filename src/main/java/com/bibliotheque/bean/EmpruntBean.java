package com.bibliotheque.bean;

import com.bibliotheque.model.Emprunt;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Utilisateur;
import com.bibliotheque.service.EmpruntService;
import com.bibliotheque.service.LivreService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class EmpruntBean implements Serializable {

    private final EmpruntService empruntService = new EmpruntService();
    private final LivreService livreService = new LivreService();

    private List<Emprunt> emprunts;
    private List<Livre> catalogue;

    @Inject
    private AuthBean authBean;

    @PostConstruct
    public void init() {
        chargerTout();
    }

    public void chargerTout() {
        emprunts = empruntService.getAll();
        catalogue = livreService.getAll();
    }

    public void demander(Livre livre) {
        Utilisateur user = authBean.getUtilisateurConnecte();
        if (user != null) {
            empruntService.demanderEmprunt(user, livre);
            chargerTout();
        }
    }

    public void valider(Emprunt emprunt) {
        empruntService.validerEmprunt(emprunt);
        chargerTout();
    }

    public void refuser(Emprunt emprunt) {
        empruntService.refuserEmprunt(emprunt);
        chargerTout();
    }

    public void retourner(Emprunt emprunt) {
        empruntService.retournerLivre(emprunt);
        chargerTout();
    }

    public List<Emprunt> getEmprunts() {
        return emprunts;
    }

    public List<Livre> getCatalogue() {
        return catalogue;
    }

    public List<Emprunt> getMesEmprunts() {
        Utilisateur user = authBean.getUtilisateurConnecte();
        if (user == null) {
            return List.of();
        }
        return empruntService.getByUtilisateur(user);
    }
}