create trigger trg_relation
 Before INSERT OR UPDATE on relation
for each row
declare 
 id number;
BEGIN 
   select nvl(iduser1,0) 
   into id  
   from relation 
   where iduser1=:new iduser2 and iduser2=: new iduser1; 
 if(id=0)
  raise_application_error(-20100,‘Impossible d’ajouter cette personne’);
 endIf;
END;


