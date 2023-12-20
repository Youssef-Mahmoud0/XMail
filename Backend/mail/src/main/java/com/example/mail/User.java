package com.example.mail;

import java.util.ArrayList;

public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String dob;
    private int userID;
    private String filePath;
    private InboxFolder inboxFolder;
    private SentFolder sentFolder;
    private DraftFolder draftFolder;
    private TrashInboxFolder trashInboxFolder;
    private TrashSentFolder trashSentFolder;
    private TrashDraftFolder trashDraftFolder;
    private ArrayList<Contact> contacts;
    private ArrayList<CustomFolder> customFolders;
//    public FolderFactory folderFactory = new FolderFactory();
    public User(UserDto user, int id){
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dob = user.getDob();
        this.userID = id;
        this.inboxFolder = null;
        this.sentFolder = null;
        this.draftFolder = null;
        this.trashInboxFolder = null;
        this.trashSentFolder = null;
        this.trashDraftFolder = null;
        this.contacts = null;
        this.customFolders = null;
    }
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

    public InboxFolder getInboxFolder() {
        FolderFactory folderFactory = new FolderFactory();
        if(this.inboxFolder == null)
            return (InboxFolder) folderFactory.createFolder("inbox");
        return this.inboxFolder;
    }

    public void setInboxFolder(InboxFolder inboxFolder) {
        this.inboxFolder = inboxFolder;
    }

    public SentFolder getSentFolder() {
        FolderFactory folderFactory = new FolderFactory();
        if(this.sentFolder == null)
            return (SentFolder) folderFactory.createFolder("sent");
        return this.sentFolder;
    }

    public void setSentFolder(SentFolder sentFolder) {
        this.sentFolder = sentFolder;
    }

    public DraftFolder getDraftFolder() {
        FolderFactory folderFactory = new FolderFactory();
        if (this.draftFolder == null)
            return (DraftFolder) folderFactory.createFolder("draft");
        return this.draftFolder;
    }
    public void setDraftFolder(DraftFolder draftFolder) {
        this.draftFolder = draftFolder;
    }
    public void addInbox(Mail mail){
        this.inboxFolder.addMail(mail);
    }
    public void addDraft(Mail mail){
        this.draftFolder.addMail(mail);
    }
    public void addSent(Mail mail){
        this.sentFolder.addMail(mail);
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }
    public void addContact(Contact contact){
        this.contacts.add(contact);
    }

//    public InboxFolder getInboxFolder() {
//        return inboxFolder;
//    }
//
//    public SentFolder getSentFolder() {
//        return sentFolder;
//    }
//
//    public DraftFolder getDraftFolder() {
//        return draftFolder;
//    }

    public TrashInboxFolder getTrashInboxFolder() {
        return trashInboxFolder;
    }

    public void setTrashInboxFolder(TrashInboxFolder trashInboxFolder) {
        this.trashInboxFolder = trashInboxFolder;
    }

    public TrashSentFolder getTrashSentFolder() {
        return trashSentFolder;
    }

    public void setTrashSentFolder(TrashSentFolder trashSentFolder) {
        this.trashSentFolder = trashSentFolder;
    }

    public TrashDraftFolder getTrashDraftFolder() {
        return trashDraftFolder;
    }

    public void setTrashDraftFolder(TrashDraftFolder trashDraftFolder) {
        this.trashDraftFolder = trashDraftFolder;
    }

    public ArrayList<CustomFolder> getCustomFolders() {
        return customFolders;
    }

    public void setCustomFolders(ArrayList<CustomFolder> customFolders) {
        this.customFolders = customFolders;
    }
    public void addCustomFolder(CustomFolder customFolder){
        this.customFolders.add(customFolder);
    }
}
