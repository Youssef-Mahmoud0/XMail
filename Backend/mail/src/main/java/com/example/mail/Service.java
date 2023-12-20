package com.example.mail;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@org.springframework.stereotype.Service
public class Service {
    public User currentUser;
    private final RegisteredUsers registeredUsers = new RegisteredUsers();
    private final FileService file = new FileService();

    public User signUp(UserDto user) throws NoSuchAlgorithmException {
        user.setPassword(Hashing.hashingPassword(user.getPassword()));
        User newUser = registeredUsers.addUser(user);
        if(newUser != null){
            file.generateJsonFile(newUser);
        }
        this.currentUser = newUser;
        return newUser;
    }
    public User signIn(UserDto user) throws NoSuchAlgorithmException {
        int id = registeredUsers.getUserId(user.getEmail());
        if (id!=0) {
            User newUser = file.getJsonData(id);
            if(Hashing.hashingPassword(user.getPassword()).equals(newUser.getPassword())) {
                this.currentUser = newUser;
                return newUser;
            }
        }
        return new User();
    }
    public void signOut(){
        this.currentUser = null;
    }
    public SentFolder addMail(Mail mail){
        System.out.println("YES YES NEW MAIIIIIIIIIIL !!!!!!!!!!");
        MailBuilder mailBuilder = new MailBuilder();
        mailBuilder.setTo(mail.getTo()).setMailID(mail.getMailID()).setMailType(mail.getMailType()).setlocalDate().setlocalTime();
        if(mail.getAttachments()!=null)
            mailBuilder.setAttachments(mail.getAttachments());
        if(mail.getContent() != null)
            mailBuilder.setContent(mail.getContent());
        Mail newMail = mailBuilder.build();
        this.currentUser.getSentFolder();
        this.currentUser.addSent(newMail);
        System.out.println(this.currentUser.getSentFolder().getMail());
        file.generateJsonFile(currentUser);
        mail.setFrom(this.currentUser.getEmail());
        mail.setMailType("inbox");
        setInbox(mail.getTo(), mail);
        //Coming soon...
//        setInbox(mail.getCc(),mail);
//        setInbox(mail.getBcc(),mail);
        return this.currentUser.getSentFolder();
    }
    public DraftFolder draftMail(Mail mail){
        System.out.println("Drafted hehe");
        MailBuilder mailBuilder = new MailBuilder();
        mailBuilder.setTo(mail.getTo()).setMailID(mail.getMailID()).setMailType(mail.getMailType()).setlocalDate().setlocalTime();
        if(mail.getAttachments()!=null)
            mailBuilder.setAttachments(mail.getAttachments());
        if(mail.getContent() != null)
            mailBuilder.setContent(mail.getContent());
        Mail newMail = mailBuilder.build();
        this.currentUser.getDraftFolder();
        this.currentUser.addDraft(newMail);
        return this.currentUser.getDraftFolder();
    }
    public TrashFolder trashMail(Mail mail){
        this.currentUser.addTrash(mail);
        if(mail.getMailType()=="inbox"){
            int index = this.currentUser.getInboxFolder().getMail().indexOf(mail);
            this.currentUser.getInboxFolder().getMail().remove(index);
        } else if (mail.getMailType()=="sent"){
            int index = this.currentUser.getSentFolder().getMail().indexOf(mail);
            this.currentUser.getSentFolder().getMail().remove(index);
        } else if (mail.getMailType()=="draft") {
            int index = this.currentUser.getDraftFolder().getMail().indexOf(mail);
            this.currentUser.getDraftFolder().getMail().remove(index);
        }
        return this.currentUser.getTrashFolder();
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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}