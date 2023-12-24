package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public interface Criteria {
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter);
}
