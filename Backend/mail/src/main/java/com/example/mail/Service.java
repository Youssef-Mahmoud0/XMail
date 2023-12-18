package com.example.mail;

import java.security.NoSuchAlgorithmException;

@org.springframework.stereotype.Service
public class Service {
//    public User currentuser;
    public UserManagement userManagement;
    private final RegisteredUsers registeredUsers = new RegisteredUsers();
    private final FileService file = new FileService();

    public boolean signUp(UserDto user) throws NoSuchAlgorithmException {
        user.setPassword(Hashing.hashingPassword(user.getPassword()));
        User newUser = registeredUsers.addUser(user);
        if(newUser != null){
            file.generateJsonFile(newUser);
        }
//        this.currentuser = newUser;
        this.userManagement.setCurrentUser(newUser);
        return newUser != null;
    }
    public boolean signIn(UserDto user) throws NoSuchAlgorithmException {
        int id = registeredUsers.getUserId(user.getEmail());
        if (id!=0) {
            User newUser = file.getJsonData(id);
            if(Hashing.hashingPassword(user.getPassword()).equals(newUser.getPassword())) {
                this.userManagement.setCurrentUser(newUser);
//                this.currentuser = newUser;
                return true;
            }
            return false;
        }
        return false;
    }
    public void signOut(){
//        this.currentuser = null;
        this.userManagement.setCurrentUser(null);
    }
    public void addInbox(Mail mail){
        this.userManagement.addUserInbox(mail);
    }
    public void addDraft(Mail mail){
        this.userManagement.addUserDraft(mail);
    }
    public void addSent(Mail mail){
        this.userManagement.addUserSent(mail);
    }
    public void addContact(Contact contact){
        this.userManagement.addUserContact(contact);
    }
}