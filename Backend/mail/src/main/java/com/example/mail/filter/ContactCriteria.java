package com.example.mail.filter;

import com.example.mail.Contact;

import java.util.ArrayList;

public class ContactCriteria {
    public ContactCriteria() {
    }

    public ArrayList<Contact> meetCriteria(ArrayList<Contact> contacts, String filter){
        ArrayList<Contact> filteredContacts = new ArrayList<>();
        for (Contact contact : contacts){
            if (contact.getName().toLowerCase().contains(filter.toLowerCase())){
                filteredContacts.add(contact);
            }
            else{
                for (String email : contact.getEmails()){
                    if (email.toLowerCase().contains(filter.toLowerCase())){
                        filteredContacts.add(contact);
                        break;
                    }
                }
            }
        }
        return filteredContacts;
    }
}
