package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;
import java.util.HashMap;

public interface CompositeCriteria {
    public ArrayList<Mail> meetCriteria(ArrayList<Mail>mails, HashMap<String,String> hashMap);
}
