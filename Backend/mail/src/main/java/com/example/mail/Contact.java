package com.example.mail;

import java.util.ArrayList;

public class Contact implements Comparable<Contact>{
    private String name;
    ArrayList <String> emails;

    public Contact(String name, ArrayList<String> emails) {
        this.name = name;
        if(emails != null)
            this.emails = emails;
        else
            this.emails = new ArrayList<>();
    }
    public Contact(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEmails() {
        if (this.emails == null)
            return new ArrayList<>();
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }
    public void addEmail(String email){
        this.getEmails();
        this.emails.add(email);
    }
    @Override
    public int compareTo(Contact other) {
        return this.name.compareTo(other.getName());
    }
}
