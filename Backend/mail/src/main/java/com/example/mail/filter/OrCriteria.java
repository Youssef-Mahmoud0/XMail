package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public class OrCriteria implements CompositeCriteria{
    private Criteria firstCriteria;
    private Criteria secondCriteria;

    public OrCriteria(Criteria firstCriteria, Criteria secondCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
    }
    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter1, String filter2) {
        ArrayList<Mail> firstFilteredMails = firstCriteria.meetCriteria(mails,filter1);
        ArrayList<Mail> secondFilteredMails = secondCriteria.meetCriteria(mails,filter2);
        for (Mail mail : secondFilteredMails){
            if (!firstFilteredMails.contains(mail))
                firstFilteredMails.add(mail);
        }
        return firstFilteredMails;
    }
}
