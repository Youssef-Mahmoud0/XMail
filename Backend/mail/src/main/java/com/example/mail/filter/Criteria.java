package com.example.mail.filter;

import com.example.mail.Mail;

import java.util.ArrayList;

public interface Criteria {
     ArrayList<Mail> meetCriteria(ArrayList<Mail> mails, String filter);
     Boolean matches(Mail mail,String filter);
}
