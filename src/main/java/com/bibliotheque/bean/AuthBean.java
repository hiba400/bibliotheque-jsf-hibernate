package com.bibliotheque.bean;

import com.bibliotheque.model.Role;
import com.bibliotheque.model.Utilisateur;
import com.bibliotheque.service.AuthService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthBean implements Serializable {

    private String email;
    private String password;
    private Utilisateur utilisateurConnecte;

    private final AuthService authService = new AuthService();

    public String login() {
        utilisateurConnecte = authService.login(email, password);

        if (utilisateurConnecte != null) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("user", utilisateurConnecte);

            if (utilisateurConnecte.getRole() == Role.ADMIN) {
                return "/admin/dashboard.xhtml?faces-redirect=true";
            } else {
                return "/user/dashboard.xhtml?faces-redirect=true";
            }
        }

        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        utilisateurConnecte = null;
        email = null;
        password = null;
        return "/login.xhtml?faces-redirect=true";
    }

    public void checkAdminAccess() throws IOException {
        if (utilisateurConnecte == null || utilisateurConnecte.getRole() != Role.ADMIN) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
        }
    }

    public void checkUserAccess() throws IOException {
        if (utilisateurConnecte == null || utilisateurConnecte.getRole() != Role.ADHERENT) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
        }
    }

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

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
}