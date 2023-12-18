package com.example.mail;

import java.util.ArrayList;

public class MailBuilder {
    private String subject;
    private ArrayList<String> to;
    private ArrayList<String> cc;
    private ArrayList<String> bcc;
    private String from;
    private String content;
    private ArrayList<String> attachments;
    private String date;
    private int priority;
    private int mailID;

    public MailBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public MailBuilder setTo(ArrayList<String> to) {
        this.to = to;
        return this;
    }

    public MailBuilder setCc(ArrayList<String> cc) {
        this.cc = cc;
        return this;
    }

    public MailBuilder setBcc(ArrayList<String> bcc) {
        this.bcc = bcc;
        return this;
    }

    public MailBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public MailBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public MailBuilder setAttachments(ArrayList<String> attachments) {
        this.attachments = attachments;
        return this;
    }

    public MailBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public MailBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public MailBuilder setMailID(int mailID) {
        this.mailID = mailID;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public ArrayList<String> getCc() {
        return cc;
    }

    public ArrayList<String> getBcc() {
        return bcc;
    }

    public String getFrom() {
        return from;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public String getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }

    public int getMailID() {
        return mailID;
    }

    public Mail build(){
        return new Mail(this);
    }
}
