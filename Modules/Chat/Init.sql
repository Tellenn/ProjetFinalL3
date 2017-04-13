CREATE TABLE salons (
idsalon number(6),
constraint pk_salons primary key (salons)
)

CREATE TABLE droit_salons ( 
idsalon number(6),
iduser number(6),
constraint pk_droit_salons primary key (idsalon, iduser),
constraint fk_droit_salons_salon foreign key (idsalon) references salons(idsalon),
constraint fk_droit_salons_user forgeign key (iduser) references user(iduser)
)
