package com.example.projet_123.bean;

import com.example.projet_123.model.Emprunt;
import com.example.projet_123.model.Livre;
import com.example.projet_123.model.Utilisateur;
import com.example.projet_123.service.EmpruntService;
import com.example.projet_123.service.LivreService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class EmpruntBean implements Serializable {

    @Inject
    private EmpruntService empruntService;
    @Inject
    private LivreService livreService;
    @Inject
    private AuthBean authBean;

    private List<Emprunt> emprunts;
    private List<Livre> catalogue;


    @PostConstruct
    public void init() {
        chargerTout();
    }

    public void chargerTout() {
        emprunts = empruntService.getAll();
        catalogue = livreService.getAll();
    }

    public void emprunter(Livre livre) {
        Utilisateur user = authBean.getUtilisateurConnecte();
        if (user != null) {
            try {
                empruntService.demanderEmprunt(user, livre);
                jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                        new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_INFO,
                                "Succès", "Demande d'emprunt envoyée pour : " + livre.getTitre()));
                chargerTout();
            } catch (Exception e) {
                jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                        new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                                "Erreur", e.getMessage()));
            }
        } else {
            jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                    new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_WARN,
                            "Attention", "Vous devez être connecté pour emprunter."));
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

    public List<Emprunt> getEmpruntsByUser(Long userId) {
        return empruntService.getByUtilisateurId(userId);
    }

    public List<Emprunt> getMesEmpruntsEnCours() {
        return getMesEmprunts().stream()
                .filter(e -> e.getStatut() == com.example.projet_123.model.StatutEmprunt.VALIDE || 
                            e.getStatut() == com.example.projet_123.model.StatutEmprunt.EN_ATTENTE)
                .toList();
    }
}