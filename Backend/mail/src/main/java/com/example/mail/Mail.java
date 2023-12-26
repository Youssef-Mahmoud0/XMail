package com.example.mail;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Mail implements Serializable, Comparable {
    private String subject;
    private ArrayList <String> to;
    private ArrayList<String> cc;
    private ArrayList<String> bcc;
    private String from;
    private String content;
    private ArrayList<Attachment> Attachments;
    private int priority;
    private int mailID;
    private LocalDate localDate;
    private LocalTime localTime;
    private String mailType;
    public Mail(){}
    public Mail(MailBuilder mailBuilder){
        this.mailID = mailBuilder.getMailID();
        this.to = mailBuilder.getTo();
        this.subject = mailBuilder.getSubject();
        this.from = mailBuilder.getFrom();
        this.cc = mailBuilder.getCc();
        this.bcc = mailBuilder.getBcc();
        this.content = mailBuilder.getContent();
        this.Attachments = mailBuilder.getAttachments();
        this.priority = mailBuilder.getPriority();
        this.localDate = mailBuilder.getLocalDate();
        this.localTime = mailBuilder.getLocalTime();
        this.mailType = mailBuilder.getMailType();
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public ArrayList<String> getCc() {
        return cc;
    }

    public void setCc(ArrayList<String> cc) {
        this.cc = cc;
    }

    public ArrayList<String> getBcc() {
        return bcc;
    }

    public void setBcc(ArrayList<String> bcc) {
        this.bcc = bcc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Attachment> getAttachments() {
        return Attachments;
    }

    public void setAttachments(ArrayList<Attachment> Attachments) {
        this.Attachments = Attachments;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMailID() {
        return mailID;
    }

    public void setMailID(int mailID) {
        this.mailID = mailID;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    @Override
    public int compareTo(Object o) {
        Mail mail = (Mail) o;
        return Integer.compare(this.priority,mail.getPriority());
    }
}
