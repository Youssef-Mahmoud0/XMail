package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public interface CompositeCriteria {
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter1,String filter2);
}
