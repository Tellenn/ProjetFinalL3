  create table user(
                   iduser number primary key,
                   username varchar not null,
                   passwor varchar not null)

create table admin(
	           idadmin number primary key,
	           adminname varchar not null,
	           adminpassword varchar not null);

create table relation(
	           iduser1 number references user(iduser),
	           iduser2 number references user(iduser),
	           constraint pk_relation primary key(iduser1,iduser2));

