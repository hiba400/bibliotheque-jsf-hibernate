-- Script d'initialisation des utilisateurs de test
-- À exécuter sur la base de données MySQL bibliotheque_db

-- Supprimer les utilisateurs existants pour éviter les doublons
DELETE FROM utilisateurs WHERE email IN ('admin@biblio.com', 'user@biblio.com');

-- Insérer l'administrateur
INSERT INTO utilisateurs (email, password, nom, role) 
VALUES ('admin@biblio.com', 'admin123', 'Administrateur', 'ADMIN');

-- Insérer l'utilisateur de test
INSERT INTO utilisateurs (email, password, nom, role) 
VALUES ('user@biblio.com', 'user123', 'Utilisateur Test', 'ADHERENT');

-- Vérifier les utilisateurs créés
SELECT * FROM utilisateurs;
