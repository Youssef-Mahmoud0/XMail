package com.example.mail;

public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String dob;
    private String filePath;
    private int userID;
    private InboxFolder inboxFolder;
    private SentFolder sentFolder;
    private DraftFolder draftFolder;
    public FolderFactory folderFactory = new FolderFactory();
    public User(){
        this.inboxFolder = null;
        this.sentFolder = null;
        this.draftFolder = null;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public InboxFolder getInstanceInboxFolder() {
        if(this.inboxFolder == null)
            return (InboxFolder) folderFactory.createFolder("inbox");
        return this.inboxFolder;
    }

    public void setInboxFolder(InboxFolder inboxFolder) {
        this.inboxFolder = inboxFolder;
    }

    public SentFolder getInstanceSentFolder() {
        if(this.sentFolder == null)
            return (SentFolder) folderFactory.createFolder("sent");
        return this.sentFolder;
    }

    public void setSentFolder(SentFolder sentFolder) {
        this.sentFolder = sentFolder;
    }

    public DraftFolder getInstanceDraftFolder() {
        if (this.draftFolder == null)
            return (DraftFolder) folderFactory.createFolder("draft");
        return this.draftFolder;
    }
    public void setDraftFolder(DraftFolder draftFolder) {
        this.draftFolder = draftFolder;
    }
//    public void addInbox(MailParent mail){
//        this.inboxFolder.addMail(mail);
//    }
//    public void addDraft(MailParent mail){
//        this.draftFolder.addMail(mail);
//    }
//    public void addSent(MailParent mail){
//        this.sentFolder.addMail(mail);
//    }

}
