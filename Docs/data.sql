----------------------------------
--								--
--		MODULE : Users			--
--								--
----------------------------------


-- Utilisateur(iduser,userName,userPassword)
INSERT INTO Utilisateur VALUES (1,'Kyllian','MIAGE');
INSERT INTO Utilisateur VALUES (2,'Ibrahima','MIAGE');
INSERT INTO Utilisateur VALUES (3,'Carine','MIAGE');
INSERT INTO Utilisateur VALUES (4,'Antoine','MIAGE');
INSERT INTO Utilisateur VALUES (5,'Quentin','MIAGE');

-- Admin(idAdmin)
INSERT INTO Admin VALUES (1);
INSERT INTO Admin VALUES (3);
INSERT INTO Admin VALUES (4);

--Relation(idUser1,idUser2)
INSERT INTO Relation VALUES(1,2);
INSERT INTO Relation VALUES(1,3);
INSERT INTO Relation VALUES(2,3);
INSERT INTO Relation VALUES(3,4);
INSERT INTO Relation VALUES(4,2);
INSERT INTO Relation VALUES(5,1);
INSERT INTO Relation VALUES(5,3);

--Salons(idSalons)
INSERT INTO Salons VALUES(1);
INSERT INTO Salons VALUES(2);
INSERT INTO Salons VALUES(3);
INSERT INTO Salons VALUES(4);
INSERT INTO Salons VALUES(5);

--DroitSalons(idSalons,idUser)
INSERT INTO DroitSalons VALUES(1,1);
INSERT INTO DroitSalons VALUES(1,2);
INSERT INTO DroitSalons VALUES(1,3);
INSERT INTO DroitSalons VALUES(1,4);
INSERT INTO DroitSalons VALUES(1,5);
INSERT INTO DroitSalons VALUES(2,2);
INSERT INTO DroitSalons VALUES(2,3);
INSERT INTO DroitSalons VALUES(3,3);
INSERT INTO DroitSalons VALUES(4,5);
INSERT INTO DroitSalons VALUES(4,4);
INSERT INTO DroitSalons VALUES(5,1);

--Evenement(idEvenement)

INSERT INTO Evenement VALUES(1);
INSERT INTO Evenement VALUES(2);
INSERT INTO Evenement VALUES(3);
INSERT INTO Evenement VALUES(4);
INSERT INTO Evenement VALUES(5);

--Participant(idSalons,idUser)
INSERT INTO Participant VALUES(1,1);
INSERT INTO Participant VALUES(1,2);
INSERT INTO Participant VALUES(1,3);
INSERT INTO Participant VALUES(1,4);
INSERT INTO Participant VALUES(1,5);
INSERT INTO Participant VALUES(2,2);
INSERT INTO Participant VALUES(2,3);
INSERT INTO Participant VALUES(3,3);
INSERT INTO Participant VALUES(4,5);
INSERT INTO Participant VALUES(4,4);
INSERT INTO Participant VALUES(5,1);

--Fichier(idFichier)

INSERT INTO Fichier VALUES(1);
INSERT INTO Fichier VALUES(2);
INSERT INTO Fichier VALUES(3);
INSERT INTO Fichier VALUES(4);
INSERT INTO Fichier VALUES(5);

--Dossier(idDossier)

INSERT INTO Dossier VALUES(1);
INSERT INTO Dossier VALUES(2);
INSERT INTO Dossier VALUES(3);
INSERT INTO Dossier VALUES(4);
INSERT INTO Dossier VALUES(5);

--DossierDansDossier(idDossierFils,idDossierPere)

INSERT INTO DossierDansDossier VALUES(1,2);
INSERT INTO DossierDansDossier VALUES(1,3);
INSERT INTO DossierDansDossier VALUES(2,4);
INSERT INTO DossierDansDossier VALUES(4,5);

--FichierDansDossier(idFichier,idDossier)

INSERT INTO FichierDansDossier VALUES(1,1);
INSERT INTO FichierDansDossier VALUES(2,2);
INSERT INTO FichierDansDossier VALUES(3,3);
INSERT INTO FichierDansDossier VALUES(4,4);
INSERT INTO FichierDansDossier VALUES(5,5);

--DroitDossier(idDossier,idUser)

INSERT INTO DroitFichier VALUES(1,1);
INSERT INTO DroitFichier VALUES(1,2);
INSERT INTO DroitFichier VALUES(1,3);
INSERT INTO DroitFichier VALUES(1,4);
INSERT INTO DroitFichier VALUES(1,5);
INSERT INTO DroitFichier VALUES(2,2);
INSERT INTO DroitFichier VALUES(2,3);
INSERT INTO DroitFichier VALUES(2,4);
INSERT INTO DroitFichier VALUES(3,3);
INSERT INTO DroitFichier VALUES(3,4);
INSERT INTO DroitFichier VALUES(3,5);
INSERT INTO DroitFichier VALUES(4,3);
INSERT INTO DroitFichier VALUES(4,5);
INSERT INTO DroitFichier VALUES(5,1);
INSERT INTO DroitFichier VALUES(5,2);

--DroitFichier(idFichier,idUser)

INSERT INTO DroitFichier VALUES(1,1);
INSERT INTO DroitFichier VALUES(1,2);
INSERT INTO DroitFichier VALUES(1,3);
INSERT INTO DroitFichier VALUES(1,4);
INSERT INTO DroitFichier VALUES(1,5);
INSERT INTO DroitFichier VALUES(2,2);
INSERT INTO DroitFichier VALUES(3,5);
INSERT INTO DroitFichier VALUES(4,3);
INSERT INTO DroitFichier VALUES(5,4);
