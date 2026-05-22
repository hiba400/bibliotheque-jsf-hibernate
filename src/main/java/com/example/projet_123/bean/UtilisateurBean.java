package com.example.projet_123.bean;

import com.example.projet_123.model.Role;
import com.example.projet_123.model.Utilisateur;
import com.example.projet_123.service.UtilisateurService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UtilisateurBean implements Serializable {

    @Inject
    private UtilisateurService utilisateurService;

    private Utilisateur utilisateur = new Utilisateur();
    private List<Utilisateur> utilisateurs;
    private String plainPassword;

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
            // Nouvel adhérent : le mot de passe est obligatoire
            if (plainPassword == null || plainPassword.isBlank()) {
                jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                    new jakarta.faces.application.FacesMessage(
                        jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                        "Le mot de passe est obligatoire", null));
                return null;
            }
            utilisateur.setPassword(plainPassword);
            utilisateurService.ajouter(utilisateur);
        } else {
            // Modification : on ne change le mot de passe que si un nouveau est fourni
            if (plainPassword != null && !plainPassword.isBlank()) {
                utilisateur.setPassword(plainPassword);
            }
            utilisateurService.modifier(utilisateur);
        }

        utilisateur = new Utilisateur();
        plainPassword = null;
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
        this.plainPassword = null;
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

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public Role[] getRoles() {
        return Role.values();
    }
}