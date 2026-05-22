package com.bibliotheque.service;

import com.bibliotheque.dao.LivreDAO;
import com.bibliotheque.model.Livre;

import java.util.List;

public class LivreService {

    private final LivreDAO livreDAO = new LivreDAO();

    public void ajouter(Livre livre) {
        livreDAO.save(livre);
    }

    public void modifier(Livre livre) {
        livreDAO.update(livre);
    }

    public void supprimer(Livre livre) {
        livreDAO.delete(livre);
    }

    public List<Livre> getAll() {
        return livreDAO.findAll();
    }

    public Livre findById(Long id) {
        return livreDAO.findById(id);
    }

    public List<Livre> rechercher(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return livreDAO.findAll();
        }
        return livreDAO.search(keyword);
    }
}