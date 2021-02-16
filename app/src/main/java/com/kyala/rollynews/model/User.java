package com.kyala.rollynews.model;

import java.util.Random;

public class User {
    String id;
    String nom, contact, mdp;

    public User(String id, String nom, String contact, String mdp) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
        this.mdp = mdp;
    }

    public User() {
    }

    public User(String nom, String contact, String mdp) {
        this.nom = nom;
        this.contact = contact;
        this.mdp = mdp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
