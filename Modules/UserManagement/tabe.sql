create table user(
                   iduser number primary key check (iduser>0),
                   username varchar not null,
                   userpassword varchar not null)

create table admin(
    	           idadmin number primary key check (idadmin>0),
    	           adminpassword varchar not null);

create table relation(
    	           iduser1 number references user(iduser),
    	           iduser2 number references user(iduser),
    	           constraint pk_relation primary key(iduser1,iduser2));
	           
CREATE TABLE salons (
                   idsalon number(6),
                   constraint pk_salons primary key (salons));

CREATE TABLE droit_salons ( 
                   idsalon number(6),
                   iduser number(6) ,
                   constraint pk_droit_salons primary key (idsalon, iduser),
                   constraint fk_droit_salons_salon foreign key (idsalon) references salons(idsalon),
                   constraint fk_droit_salons_user forgeign key (iduser) references user(iduser));

create table Evenement(
                   idevent number);
                   
create table Participant(
                   idevent number references Evenement(idevent),
                   iduser number references user(iduser));
               
create table Document(
                   idDoc number(3),
                   nomDoc varchar));
    
create table Folder(
                   idFolder number,
                   nomFolder varchar, 
                   chemin varchar);


               
