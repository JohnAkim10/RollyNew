package com.kyala.rollynews.model;

public class Commentaire {
    String id, auteur, commentaire, date, idarticle;

    public Commentaire(String auteur, String commentaire, String date, String idarticle) {
        this.auteur = auteur;
        this.commentaire = commentaire;
        this.date = date;
        this.idarticle = idarticle;
    }

    public Commentaire(String id, String auteur, String commentaire, String date, String idarticle) {
        this.id = id;
        this.auteur = auteur;
        this.commentaire = commentaire;
        this.date = date;
        this.idarticle = idarticle;
    }

    public Commentaire(){

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getId(){
        return auteur +""+ date;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(String idarticle) {
        this.idarticle = idarticle;
    }
}
