package com.example.mail;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@org.springframework.stereotype.Service
public class Service {
    public User currentuser;
    private final RegisteredUsers registeredUsers = new RegisteredUsers();
    private final FileService file = new FileService();

    public User signUp(UserDto user) throws NoSuchAlgorithmException {
        user.setPassword(Hashing.hashingPassword(user.getPassword()));
        User newUser = registeredUsers.addUser(user);
        if(newUser != null){
            file.generateJsonFile(newUser);
        }
        this.currentuser = newUser;
        return newUser;
    }
    public User signIn(UserDto user) throws NoSuchAlgorithmException {
        int id = registeredUsers.getUserId(user.getEmail());
        if (id!=0) {
            User newUser = file.getJsonData(id);
            if(Hashing.hashingPassword(user.getPassword()).equals(newUser.getPassword())) {
                this.currentuser = newUser;
                return newUser;
            }
        }
        return new User();
    }
    public void signOut(){
        this.currentuser = null;
    }
    public void addMail(Mail mail){
        System.out.println("YES YES NEW MAIIIIIIIIIIL !!!!!!!!!!");
        MailBuilder mailBuilder = new MailBuilder();
        mailBuilder.setTo(mail.getTo()).setMailID(mail.getMailID()).setMailType(mail.getMailType()).setlocalDate().setlocalTime();
        if(mail.getAttachments()!=null)
            mailBuilder.setAttachments(mail.getAttachments());
        if(mail.getContent() != null)
            mailBuilder.setContent(mail.getContent());
        Mail newMail = mailBuilder.build();
        this.currentuser.getSentFolder();
        this.currentuser.addSent(newMail);
        System.out.println(this.currentuser.getSentFolder().getMail());
        file.generateJsonFile(currentuser);
        mail.setTo(null);
        mail.setFrom(this.currentuser.getEmail());
        mail.setMailType("inbox");
        setInbox(mail.getTo(), mail);
    }
    public SentFolder getSent(){
        return this.currentuser.getSentFolder();
    }
    public User getUser(String email){
        int id = registeredUsers.getUserId(email);
        if (id!=0) {
            return file.getJsonData(id);
        }
        return null;
    }
    public void setInbox(ArrayList<String> emails, Mail mail){
        for(String email:emails) {
            User user = getUser(email);
            if (user != null) {
                user.getInboxFolder();
                user.addInbox(mail);
                file.generateJsonFile(user);
            }
        }
    }
}