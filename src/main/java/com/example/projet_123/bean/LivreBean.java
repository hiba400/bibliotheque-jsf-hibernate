package com.example.projet_123.bean;

import com.example.projet_123.model.Livre;
import com.example.projet_123.service.LivreService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class LivreBean implements Serializable {

    @Inject
    private LivreService livreService;

    private Livre livre = new Livre();
    private List<Livre> livres;
    private String keyword;

    @PostConstruct
    public void init() {
        chargerLivres();
    }

    public void chargerLivres() {
        livres = livreService.getAll();
    }

    public String save() {
        if (livre.getId() == null) {
            livreService.ajouter(livre);
        } else {
            livreService.modifier(livre);
        }
        livre = new Livre();
        chargerLivres();
        return "/admin/livres.xhtml?faces-redirect=true";
    }

    public String view(Livre livre) {
        this.livre = livre;
        return "/admin/livre-details.xhtml?faces-redirect=true";
    }

    public String edit(Livre livre) {
        this.livre = livre;
        return "/admin/livre-form.xhtml?faces-redirect=true";
    }

    public String delete(Livre livre) {
        System.out.println("Tentative de suppression du livre ID: " + livre.getId());
        try {
            livreService.supprimer(livre);
            chargerLivres();
            jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_INFO, "Succès", "Livre supprimé avec succès"));
            System.out.println("Suppression réussie");
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
            jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR, "Erreur de suppression", e.getMessage()));
        }
        return null; // Reste sur la même page
    }

    public String nouveau() {
        this.livre = new Livre();
        return "/admin/livre-form.xhtml?faces-redirect=true";
    }

    public void search() {
        livres = livreService.rechercher(keyword);
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}