package com.example.mail;

public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String filePath;
    private int userID;
    private InboxFolder inbox;
    private SentFolder sent;
    private DraftFolder draft;
    public User(){
        this.inbox = null;
        this.sent = null;
        this.draft = null;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public InboxFolder getInbox() {
        return inbox;
    }

    public void setInbox(InboxFolder inbox) {
        this.inbox = inbox;
    }

    public SentFolder getSent() {
        return sent;
    }

    public void setSent(SentFolder sent) {
        this.sent = sent;
    }

    public DraftFolder getDraft() {
        return draft;
    }

    public void setDraft(DraftFolder draft) {
        this.draft = draft;
    }
    public void addInbox(MailParent mail){
        this.inbox.AddMail(mail);
    }
    public void addDraft(MailParent mail){
        this.draft.AddMail(mail);
    }
    public void addSent(MailParent mail){
        this.sent.AddMail(mail);
    }

}
