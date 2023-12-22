package com.example.mail;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
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
    private TrashFolder trashFolder;
    private ArrayList<Contact> contacts;
    private ArrayList<CustomFolder> customFolders;
    private int globalMailNumber;
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
        this.trashFolder = null;
        this.contacts = null;
        this.customFolders = null;
    }
    public User(){

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

    public int getGlobalMailNumber() {
        globalMailNumber++;
        return globalMailNumber;
    }

    public void setGlobalMailNumber(int globalMailNumber) {
        this.globalMailNumber = globalMailNumber;
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
        this.getInboxFolder();
        this.inboxFolder.addMail(mail);
    }
    public void addDraft(Mail mail){
        this.getDraftFolder();
        this.draftFolder.addMail(mail);
    }
    public void addSent(Mail mail){
        this.getSentFolder();
        this.sentFolder.addMail(mail);
    }
    public void addTrash(Mail mail){
        this.getTrashFolder();
        this.trashFolder.addMail(mail);
    }
    public TrashFolder getTrashFolder() {
        FolderFactory folderFactory = new FolderFactory();
        if (this.trashFolder == null)
            return (TrashFolder) folderFactory.createFolder("trash");
        return this.trashFolder;
    }

    public void setTrashFolder(TrashFolder trashFolder) {
        this.trashFolder = trashFolder;
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
