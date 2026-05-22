package com.bibliotheque.bean;

import com.bibliotheque.model.Livre;
import com.bibliotheque.service.LivreService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class LivreBean implements Serializable {

    private final LivreService livreService = new LivreService();

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

    public String edit(Livre livre) {
        this.livre = livre;
        return "/admin/livre-form.xhtml?faces-redirect=true";
    }

    public void delete(Livre livre) {
        livreService.supprimer(livre);
        chargerLivres();
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