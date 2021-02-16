package com.kyala.rollynews.model;

public class Info {
    String id, titre, message, image;

    public Info(){

    }

    public Info(String id, String titre, String message, String image) {
        this.id = id;
        this.titre = titre;
        this.message = message;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
