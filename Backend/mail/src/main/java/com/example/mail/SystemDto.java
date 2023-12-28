package com.example.mail;

import java.util.ArrayList;
import java.util.HashMap;

public class SystemDto {
    private String email;
    private ArrayList<Mail> sourceMails;
    private ArrayList<Mail> destinationMails;
    private Contact contact;
    private Mail mail;
    private String source;
    private String destination;
    private HashMap<String,String> hashMap;

    public SystemDto() {
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Mail> getSourceMails() {
        return sourceMails;
    }

    public void setSourceMails(ArrayList<Mail> sourceMails) {
        this.sourceMails = sourceMails;
    }

    public ArrayList<Mail> getDestinationMails() {
        return destinationMails;
    }

    public void setDestinationMails(ArrayList<Mail> destinationMails) {
        this.destinationMails = destinationMails;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
