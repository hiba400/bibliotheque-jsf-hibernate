package com.bibliotheque.bean;

import com.bibliotheque.model.Role;
import com.bibliotheque.model.Utilisateur;
import com.bibliotheque.service.UtilisateurService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UtilisateurBean implements Serializable {

    private final UtilisateurService utilisateurService = new UtilisateurService();

    private Utilisateur utilisateur = new Utilisateur();
    private List<Utilisateur> utilisateurs;

    @PostConstruct
    public void init() {
        chargerUtilisateurs();
    }

    public void chargerUtilisateurs() {
        utilisateurs = utilisateurService.getAll();
    }

    public String save() {
        if (utilisateur.getRole() == null) {
            utilisateur.setRole(Role.ADHERENT);
        }

        if (utilisateur.getId() == null) {
            utilisateurService.ajouter(utilisateur);
        } else {
            utilisateurService.modifier(utilisateur);
        }

        utilisateur = new Utilisateur();
        chargerUtilisateurs();
        return "/admin/adherents.xhtml?faces-redirect=true";
    }

    public String edit(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return "/admin/adherent-form.xhtml?faces-redirect=true";
    }

    public void delete(Utilisateur utilisateur) {
        utilisateurService.supprimer(utilisateur);
        chargerUtilisateurs();
    }

    public String nouveau() {
        this.utilisateur = new Utilisateur();
        this.utilisateur.setRole(Role.ADHERENT);
        return "/admin/adherent-form.xhtml?faces-redirect=true";
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public Role[] getRoles() {
        return Role.values();
    }
}