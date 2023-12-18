package com.example.mail;

public class UserManagement {
    private User currentUser;
    private FileService file;
    public UserManagement(){
        this.file = new FileService();
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public void updateUser(){
        file.generateJsonFile(this.currentUser);
    }
    public void addUserInbox(Mail mail){
        this.currentUser.addInbox(mail);
    }
    public void addUserDraft(Mail mail){
        this.currentUser.addDraft(mail);
    }
    public void addUserSent(Mail mail){
        this.currentUser.addSent(mail);
    }
    public void addUserContact(Contact contact) {
        this.currentUser.addContact(contact);
    }
}
