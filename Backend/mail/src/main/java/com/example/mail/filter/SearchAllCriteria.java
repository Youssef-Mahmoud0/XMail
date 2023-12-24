package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchAllCriteria {
    private CriteriaContent criteriaContent;
    private CriteriaDate criteriaDate;
    private CriteriaFrom criteriaFrom;
    private CriteriaSubject criteriaSubject;
    private CriteriaTo criteriaTo;
    private CriteriaYear criteriaYear;

    public SearchAllCriteria() {
        this.criteriaContent = new CriteriaContent();
        this.criteriaDate = new CriteriaDate();
        this.criteriaFrom = new CriteriaFrom();
        this.criteriaSubject = new CriteriaSubject();
        this.criteriaTo = new CriteriaTo();
        this.criteriaYear = new CriteriaYear();
    }

    public ArrayList<Mail> matchesCriteria(ArrayList<Mail>mails, HashMap<String,String>hashMap){
        ArrayList<Mail> filteredMails = new ArrayList<>();
        for (Mail mail: mails){
            for (String key : hashMap.keySet()) {
                if (key.equals("content")){
                    if (criteriaContent.matches(mail,hashMap.get(key))){
                        filteredMails.add(mail);
                        break;
                    }
                } else if (key.equals("subject")) {
                    if (criteriaSubject.matches(mail,hashMap.get(key))){
                        filteredMails.add(mail);
                        break;
                    }
                } else if (key.equals("from")) {
                    if (criteriaFrom.matches(mail,hashMap.get(key))){
                        filteredMails.add(mail);
                        break;
                    }
                } else if (key.equals("to")) {
                    if (criteriaTo.matches(mail,hashMap.get(key))){
                        filteredMails.add(mail);
                        break;
                    }
                } else if (key.equals("year")) {
                    if (criteriaYear.matches(mail,hashMap.get(key))){
                        filteredMails.add(mail);
                        break;
                    }
                } else if (key.equals("date")) {
                    if (criteriaDate.matches(mail,hashMap.get(key))){
                        filteredMails.add(mail);
                        break;
                    }
                }
            }
        }
        return filteredMails;
    }
}
