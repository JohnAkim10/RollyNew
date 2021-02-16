package com.kyala.rollynews.model;

import java.util.Date;

public class Message {
    String id, iduser, message, date;

    public Message(String iduser, String message) {
        this.iduser = iduser;
        this.message = message;
    }

    public Message(String id, String iduser, String message, String date) {
        this.id = id;
        this.iduser = iduser;
        this.message = message;
        this.date = date;
    }

    public Message() {
    }

    public String getId() {
        return iduser+getDate();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return new Date().toString();
    }

    public void setDate(String date) {
        this.date = date;
    }
}
