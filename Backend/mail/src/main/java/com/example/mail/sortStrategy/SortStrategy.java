package com.example.mail.sortStrategy;

import com.example.mail.Mail;

import java.util.ArrayList;

public interface SortStrategy {
    public ArrayList<Mail> sort(ArrayList<Mail> mails);
}
