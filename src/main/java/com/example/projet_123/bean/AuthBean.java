package com.example.projet_123.bean;

import com.example.projet_123.model.Role;
import com.example.projet_123.model.Utilisateur;
import com.example.projet_123.service.AuthService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthBean implements Serializable {

    private String email;
    private String password;

    // Pour l'inscription (formulaire)
    private Utilisateur utilisateur = new Utilisateur();

    // Pour la session (utilisateur réellement connecté)
    private Utilisateur utilisateurConnecte;

    @Inject
    private AuthService authService;

    public String login() {
        System.out.println("Tentative de connexion avec email: " + email);

        utilisateurConnecte = authService.login(email, password);

        if (utilisateurConnecte != null) {
            System.out.println("Connexion SUCCÈS: " + utilisateurConnecte.getNom());

            // Re-initialiser le formulaire d'inscription par précaution
            utilisateur = new Utilisateur();

            if (utilisateurConnecte.getRole() == Role.ADMIN) {
                return "/admin/dashboard.xhtml?faces-redirect=true";
            } else {
                return "/user/dashboard.xhtml?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                            "Email ou mot de passe incorrect", "Identifiants invalides"));

            // IMPORTANT : S'assurer que 'utilisateur' n'est pas null pour ne pas casser la
            // page d'inscription
            if (utilisateur == null) {
                utilisateur = new Utilisateur();
            }

            System.out.println("Connexion ÉCHEC");
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        utilisateurConnecte = null;
        utilisateur = new Utilisateur();
        email = null;
        password = null;
        return "/login.xhtml?faces-redirect=true";
    }

    public void checkAdminAccess() throws IOException {
        if (utilisateurConnecte == null || utilisateurConnecte.getRole() != Role.ADMIN) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                            + "/login.xhtml");
        }
    }

    public void checkUserAccess() throws IOException {
        if (utilisateurConnecte == null) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                            + "/login.xhtml");
        }
    }

    public String register() {
        System.out.println("Tentative d'inscription pour: " + utilisateur.getEmail());
        try {
            authService.register(utilisateur);
            FacesContext.getCurrentInstance().addMessage(null,
                    new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_INFO,
                            "Inscription réussie", "Vous pouvez maintenant vous connecter"));

            // Re-initialiser l'objet pour un nouveau formulaire
            utilisateur = new Utilisateur();
            return "/login.xhtml"; // Rester ou aller à login
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                            "Erreur d'inscription", e.getMessage()));
            return null;
        }
    }

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }

    public boolean isLoggedIn() {
        return utilisateurConnecte != null;
    }

    public Role[] getRoles() {
        return Role.values();
    }
}