package com.example.mail.sortStrategy;

import com.example.mail.Mail;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SortByPriority implements SortStrategy{
    public SortByPriority() {}

    @Override
    public ArrayList<Mail> sort(ArrayList<Mail> mails) {
        Comparator<Mail> mailComparator = Comparator.comparingInt(Mail::getPriority);
        PriorityQueue<Mail> mailQueue = new PriorityQueue<>(mailComparator);
        mailQueue.addAll(mails);
        ArrayList<Mail> priorityMails = new ArrayList<>();
        while (!mailQueue.isEmpty())
            priorityMails.add(mailQueue.poll());
        return priorityMails;
    }
}