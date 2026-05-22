package com.example.projet_123.service;

import com.example.projet_123.dao.LivreDao;
import com.example.projet_123.model.Livre;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class LivreService {

    @Inject
    private LivreDao livreDAO;

    @Inject
    private com.example.projet_123.dao.EmpruntDao empruntDao;

    public void ajouter(Livre livre) {
        livreDAO.save(livre);
    }

    public void modifier(Livre livre) {
        livreDAO.update(livre);
    }

    public void supprimer(Livre livre) {
        if (empruntDao.countByLivre(livre) > 0) {
            throw new RuntimeException("Impossible de supprimer ce livre car il est associé à un historique d'emprunts.");
        }
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