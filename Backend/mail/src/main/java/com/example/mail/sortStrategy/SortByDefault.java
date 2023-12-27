package com.example.mail.sortStrategy;

import com.example.mail.Mail;

import java.util.ArrayList;
import java.util.Collections;

public class SortByDefault implements SortStrategy{
    public SortByDefault() {}

    @Override
    public ArrayList<Mail> sort(ArrayList<Mail> mails) {
        return mails;
    }
}