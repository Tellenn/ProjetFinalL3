/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Kyllian
 */
public class Fichier {
    //TEST
    public String nom;
    
    public Fichier(){
        this.nom="Bonjour";
    }
    public Fichier(String nom){
        this.setNom(nom);
    }
    
    public String getNom (){
        return this.nom;
    }
    
    public void setNom (String nom){
        this.nom = nom;
    }
    
    public ObservableList<Fichier> getFichierObs() {
        ObservableList<Fichier> files = FXCollections.observableArrayList();
        files.add(new Fichier("1truc.jpg"));
        files.add(new Fichier("Chouette.txt"));
        return files;       
    }
}
