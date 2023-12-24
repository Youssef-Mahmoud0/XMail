package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public class AndCriteria implements CompositeCriteria{
    private Criteria firstCriteria;
    private Criteria secondCriteria;

    public AndCriteria(Criteria firstCriteria, Criteria secondCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
    }
    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter1, String filter2) {
        ArrayList<Mail> filteredMails = new ArrayList<>();
        filteredMails = firstCriteria.meetCriteria(mails,filter1);
        return secondCriteria.meetCriteria(filteredMails,filter2);
    }
}
