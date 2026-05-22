package com.example.projet_123.util;

import com.example.projet_123.dao.UtilisateurDao;
import com.example.projet_123.model.Role;
import com.example.projet_123.model.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DatabaseInitializer {

    @Inject
    private UtilisateurDao utilisateurDao;

    @PostConstruct
    public void init() {
        // Vérifier si des utilisateurs existent déjà
        if (utilisateurDao.findAll().isEmpty()) {
            // Créer un administrateur par défaut
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setEmail("admin@biblio.com");
            admin.setPassword("admin123");
            admin.setRole(Role.ADMIN);
            utilisateurDao.save(admin);

            // Créer un utilisateur de test
            Utilisateur user = new Utilisateur();
            user.setNom("Utilisateur Test");
            user.setEmail("user@biblio.com");
            user.setPassword("user123");
            user.setRole(Role.ADHERENT);
            utilisateurDao.save(user);

            System.out.println("Utilisateurs de test créés : admin@biblio.com / admin123 et user@biblio.com / user123");
        }
    }
}
