--Suppression des tables
DROP TABLE Admin CASCADE CONSTRAINTS;
DROP TABLE Relation CASCADE CONSTRAINTS;
DROP TABLE DroitSalons CASCADE CONSTRAINTS;
DROP TABLE Salons CASCADE CONSTRAINTS;
DROP TABLE Participant CASCADE CONSTRAINTS;
DROP TABLE Evenement CASCADE CONSTRAINTS;
DROP TABLE Utilisateur CASCADE CONSTRAINTS;
DROP TABLE DossierDansDossier CASCADE CONSTRAINTS;
DROP TABLE FichierDansDossier CASCADE CONSTRAINTS;
DROP TABLE DroitDossier CASCADE CONSTRAINTS;
DROP TABLE DroitFichier CASCADE CONSTRAINTS;
DROP TABLE Dossier CASCADE CONSTRAINTS;
DROP TABLE Fichier CASCADE CONSTRAINTS;

--Table Utilisateur
CREATE TABLE Utilisateur
(
	idUser 			INTEGER PRIMARY KEY,
	userName		VARCHAR2(20) NOT NULL unique,
	userPassword	VARCHAR2(20) NOT NULL,
	CONSTRAINT ck_User_idUser CHECK (idUser>0)
);
--Table Admin
CREATE TABLE Admin
(
	idUser 		INTEGER PRIMARY KEY,
   	CONSTRAINT ck_Admin_idAdmin CHECK (idUser>0),
   	CONSTRAINT fk_Admin_idAdmin FOREIGN KEY (idAdmin) REFERENCES Utilisateur(idUser)
);
--Table Relation
CREATE TABLE Relation
(
    idUser1 		INTEGER,
    idUser2 		INTEGER,
    CONSTRAINT pk_Relation PRIMARY KEY(iduser1,iduser2),
    CONSTRAINT fk_Relation_idUser1 FOREIGN KEY (idUser1) REFERENCES Utilisateur(idUser),
    CONSTRAINT fk_Relation_idUser2 FOREIGN KEY (idUser2) REFERENCES Utilisateur(idUser)
);
--Table Salons
CREATE TABLE Salons
(
    idSalon 		INTEGER PRIMARY KEY
);
--Table DroitSalons
CREATE TABLE DroitSalons
(
	idSalon 		INTEGER,
    idUser 			INTEGER,
    CONSTRAINT pk_DroitSalons PRIMARY KEY (idSalon, idUser),
    CONSTRAINT fk_DroitSalons_idSalon FOREIGN KEY (idSalon) REFERENCES salons(idSalon),
    CONSTRAINT fk_DroitSalons_idUser FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)
);
--Table Evenement
CREATE TABLE Evenement
(
    idEvenement 	INTEGER PRIMARY KEY
);
--Table Participant
CREATE TABLE Participant
(
	idEvenement 	INTEGER,
	idUser 			INTEGER,
	CONSTRAINT pk_Participant PRIMARY KEY (idEvenement, idUser),
	CONSTRAINT fk_Participant_idEvenement FOREIGN KEY (idEvenement) REFERENCES Evenement(idEvenement),
	CONSTRAINT fk_Participant_idUser FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)
);
--Table Dossier
CREATE TABLE Dossier(
    idDossier		INTEGER PRIMARY KEY
);
--Table Fichier
CREATE TABLE Fichier
(
    idFichier 		INTEGER PRIMARY KEY
);
--Table DossierDansDossier
CREATE TABLE DossierDansDossier
(
	idDossierFils 	INTEGER PRIMARY KEY,
	idDossierPere 	INTEGER,
	CONSTRAINT fk_DdsD_idDossierFils FOREIGN KEY (idDossierFils) REFERENCES Dossier(idDossier),
	CONSTRAINT fk_DdsD_idDossierPere FOREIGN KEY (idDossierPere) REFERENCES Dossier(idDossier)
);
--Table FichierDansDossier
CREATE TABLE FichierDansDossier
(
	idFichier 		INTEGER PRIMARY KEY,
	idDossier 		INTEGER,
	CONSTRAINT fk_FdsD_idFichier FOREIGN KEY (idFichier) REFERENCES Fichier(idFichier),
	CONSTRAINT fk_FdsD_idDossier FOREIGN KEY (idDossier) REFERENCES Dossier(idDossier)
);
--Table DroitDossier
CREATE TABLE DroitDossier
(
	idDossier 		INTEGER,
	idUser 			INTEGER,
	CONSTRAINT pk_DroitDossier PRIMARY KEY (idDossier, idUser),
	CONSTRAINT fk_DroitDossier_idDossier FOREIGN KEY (idDossier) REFERENCES Dossier(idDossier),
	CONSTRAINT fk_DroitDossier_idUser FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)
);
--Table DroitFichier
CREATE TABLE DroitFichier
(
	idFichier 		INTEGER,
	idUser 			INTEGER,
	CONSTRAINT pk_DroitFichier PRIMARY KEY (idFichier, idUser),
	CONSTRAINT fk_DroitFichier_idFichier FOREIGN KEY (idFichier) REFERENCES Fichier(idFichier),
	CONSTRAINT fk_DroitFichier_idUser FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)
);