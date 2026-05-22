package com.example.projet_123.bean;

import com.example.projet_123.model.Role;
import com.example.projet_123.model.Utilisateur;
import com.example.projet_123.service.UtilisateurService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Named
@RequestScoped
public class SetupBean {

    @PersistenceContext
    private EntityManager em;

    private String message = "";

    @Transactional
    public String createTestUsers() {
        try {
            // Supprimer les anciens utilisateurs de test s'ils existent
            em.createQuery("DELETE FROM Utilisateur u WHERE u.email IN ('admin@biblio.com', 'user@biblio.com')")
              .executeUpdate();

            // Créer l'administrateur
            Utilisateur admin = new Utilisateur();
            admin.setNom("Administrateur");
            admin.setEmail("admin@biblio.com");
            admin.setPassword("admin123");
            admin.setRole(Role.ADMIN);
            em.persist(admin);

            // Créer l'utilisateur
            Utilisateur user = new Utilisateur();
            user.setNom("Utilisateur Test");
            user.setEmail("user@biblio.com");
            user.setPassword("user123");
            user.setRole(Role.ADHERENT);
            em.persist(user);

            message = "✅ Utilisateurs créés avec succès !<br>Admin: admin@biblio.com / admin123<br>User: user@biblio.com / user123";
            System.out.println("UTILISATEURS DE TEST CRÉÉS AVEC SUCCÈS");
        } catch (Exception e) {
            message = "❌ Erreur: " + e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
