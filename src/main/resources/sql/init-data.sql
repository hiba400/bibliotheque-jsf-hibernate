-- Création des tables si elles n'existent pas
CREATE DATABASE IF NOT EXISTS bibliotheque_db;
USE bibliotheque_db;

-- Table des utilisateurs
CREATE TABLE IF NOT EXISTS utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'ADHERENT') NOT NULL
);

-- Table des livres
CREATE TABLE IF NOT EXISTS livres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255) NOT NULL,
    categorie VARCHAR(255) NOT NULL,
    disponible BOOLEAN DEFAULT TRUE
);

-- Table des emprunts
CREATE TABLE IF NOT EXISTS emprunts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dateDemande DATE,
    dateRetour DATE,
    dateValidation DATE,
    statut ENUM('EN_ATTENTE', 'VALIDE', 'REFUSE', 'RETOURNE') NOT NULL,
    livre_id BIGINT NOT NULL,
    utilisateur_id BIGINT NOT NULL,
    FOREIGN KEY (livre_id) REFERENCES livres(id),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
);

-- Insertion d'un utilisateur de test administrateur
INSERT INTO utilisateurs (nom, email, password, role) 
VALUES 
('Admin', 'admin@biblio.com', 'admin123', 'ADMIN')
ON DUPLICATE KEY UPDATE email = email;

-- Insertion d'un utilisateur de test adhérent
INSERT INTO utilisateurs (nom, email, password, role) 
VALUES 
('Utilisateur Test', 'user@biblio.com', 'user123', 'ADHERENT')
ON DUPLICATE KEY UPDATE email = email;

-- Insertion de quelques livres de test
INSERT INTO livres (titre, auteur, categorie, disponible) VALUES
('Le Petit Prince', 'Antoine de Saint-Exupéry', 'Roman', TRUE),
('1984', 'George Orwell', 'Science-Fiction', TRUE),
('Les Misérables', 'Victor Hugo', 'Classique', TRUE)
ON DUPLICATE KEY UPDATE titre = VALUES(titre);
