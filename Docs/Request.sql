----------------------------------
--								--
--		MODULE : CHAT			--
--								--
----------------------------------

-- Récupération des salons accessible par un utilisateur 'X'

SELECT idsalon
FROM droitSalons
WHERE iduser='X'

-- Récupération des clients d'un salon 'Y'

SELECT iduser
FROM droitSalons
WHERE idsalon='Y'


----------------------------------
--								--
--		MODULE : Calendrier		--
--								--
----------------------------------

-- Récupération des évènements d'un utilisateur 'X'

SELECT idEvenement
FROM Participant
WHERE iduser='X'

-- Récupération des participant d'un évènement 'Y'

SELECT iduser
FROM Participant
WHERE idEvenement='Y'


----------------------------------
--								--
--		MODULE : USERS			--
--								--
----------------------------------

-- Est-ce qu'un utilisateur 'X' est admin ?

Select idAdmin
FROM Admin
WHERE idAdmin='X'

-- Est-ce que l'utilisateur 'X' à un mot de passe 'Y' ?

SELECT idUser
FROM Utilisateur
WHERE userName='X' and userPassword='Y'

-- Récupération de la liste de relation d'un utilisateur 'X'

SELECT idUser1 as idUser
FROM Relation
WHERE idUser2='X'
UNION
SELECT idUser2 as idUser
FROM relation
WHERE idUser1='X'

--Vérification que l'utilisateur 'X' et 'Y' sont en relation

SELECT idUser1 as idUser
FROM Relation
WHERE idUser2='X' and idUser1='Y'
UNION
SELECT idUser2 as idUser
FROM relation
WHERE idUser1='X' and idUser2='Y'

-- Trigger pour éviter le doublon d'ami

create or replace trigger trg_relation
before INSERT OR UPDATE on relation
for each row
declare 
	id number;
BEGIN 
	select nvl(iduser1,0) into id
	from relation
	where iduser1=:new.iduser2 and iduser2=:new.iduser1; 
	if(id=0) then
		raise_application_error(-20100,'Ces personnes sont déjà en relation!');
	End if;
End;
/


----------------------------------
--								--
--		MODULE : GED			--
--								--
----------------------------------

-- Récuperation des fichiers du dossier 'X' pour l'utilisateur 'Y'

SELECT idFichier
FROM FichierDansDossier natural join (
	SELECT idFichier
	FROM DroitFichier
	WHERE idUser='Y')
WHERE idDossier='X'

-- Récuperation des dossiers du dossier 'X' pour l'utilisateur 'Y'

SELECT idDossier
FROM FichierDansDossier natural join (
	SELECT idDossier
	FROM DroitDossier
	WHERE idUser='Y')
WHERE idDossier='X'
