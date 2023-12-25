package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;
import java.util.HashMap;

public class AndCriteria implements CompositeCriteria{
    private CriteriaContent criteriaContent;
    private CriteriaDate criteriaDate;
    private CriteriaFrom criteriaFrom;
    private CriteriaSubject criteriaSubject;
    private CriteriaTo criteriaTo;
    private CriteriaYear criteriaYear;
    private CriteriaPriority criteriaPriority;
    private CriteriaAttachment criteriaAttachment;

    public AndCriteria() {
        this.criteriaContent = new CriteriaContent();
        this.criteriaDate = new CriteriaDate();
        this.criteriaFrom = new CriteriaFrom();
        this.criteriaSubject = new CriteriaSubject();
        this.criteriaTo = new CriteriaTo();
        this.criteriaYear = new CriteriaYear();
        this.criteriaPriority = new CriteriaPriority();
        this.criteriaAttachment = new CriteriaAttachment();
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, HashMap<String, String> hashMap) {
        ArrayList<Mail> filteredMails = mails;
        for (Mail mail: mails){
            for (String key : hashMap.keySet()) {
                if (key.equals("content") && hashMap.get(key)!=null && hashMap.get(key)!=""){
                    filteredMails = criteriaContent.meetCriteria(filteredMails,hashMap.get(key));
                } else if (key.equals("subject")&& hashMap.get(key)!=null && hashMap.get(key)!="") {
                    filteredMails = criteriaSubject.meetCriteria(filteredMails,hashMap.get(key));
                } else if (key.equals("from")&& hashMap.get(key)!=null && hashMap.get(key)!="") {
                    filteredMails = criteriaFrom.meetCriteria(filteredMails,hashMap.get(key));
                } else if (key.equals("to")&& hashMap.get(key)!=null && hashMap.get(key)!="") {
                    filteredMails = criteriaTo.meetCriteria(filteredMails,hashMap.get(key));
                } else if (key.equals("year")&& hashMap.get(key)!=null && hashMap.get(key)!="") {
                    filteredMails = criteriaYear.meetCriteria(filteredMails,hashMap.get(key));
                }else if (key.equals("date")&& hashMap.get(key)!=null && hashMap.get(key)!="") {
                    filteredMails = criteriaDate.meetCriteria(filteredMails,hashMap.get(key));
                } else if (key.equals("priority")&& hashMap.get(key)!=null && hashMap.get(key)!="") {
                    filteredMails = criteriaPriority.meetCriteria(filteredMails,hashMap.get(key));
                }  else if (key.equals("attachmentName")&& hashMap.get(key)!=null && hashMap.get(key)!="") {
                    filteredMails = criteriaAttachment.meetCriteria(filteredMails,hashMap.get(key));
                }
            }
        }
        return filteredMails;
    }

//    @Override
//    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter1, String filter2) {
//        ArrayList<Mail> filteredMails = new ArrayList<>();
//        filteredMails = firstCriteria.meetCriteria(mails,filter1);
//        return secondCriteria.meetCriteria(filteredMails,filter2);
//    }
}
