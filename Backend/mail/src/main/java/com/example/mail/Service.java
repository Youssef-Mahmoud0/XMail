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
//        mail.setFrom(this.currentUser.getEmail());
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
        mail.setMailType("draft");
        this.currentUser.getDraftFolder();
        this.currentUser.addDraft(newMail);
        file.generateJsonFile(currentUser);
//        mail.setFrom(this.currentUser.getEmail());
        return this.currentUser.getDraftFolder();
    }
    public TrashFolder trashMail(Mail mail){
        System.out.println("TRASH WORKED LESGOOOO");
        MailBuilder mailBuilder = new MailBuilder();
        mailBuilder.setTo(mail.getTo()).setMailID(mail.getMailID()).setMailType(mail.getMailType()).setlocalDate().setlocalTime();
        if(mail.getAttachments()!=null)
            mailBuilder.setAttachments(mail.getAttachments());
        if(mail.getContent() != null)
            mailBuilder.setContent(mail.getContent());
        Mail newMail = mailBuilder.build();
        this.currentUser.setTrashFolder(this.currentUser.getTrashFolder());
        this.currentUser.addTrash(newMail);
        System.out.println(mail.getMailType());
        int index = -1;
        if(mail.getMailType().equals("inbox")){
            for (Mail mailToFind:this.currentUser.getInboxFolder().getMail()){
                if (newMail.getMailID()==mailToFind.getMailID()){
                    index = this.currentUser.getInboxFolder().getMail().indexOf(mailToFind);
                    break;
                }
            }
            this.currentUser.getInboxFolder().getMail().remove(index);
        } else if (mail.getMailType().equals("sent")){
            for (Mail mailToFind:this.currentUser.getSentFolder().getMail()){
                if (newMail.getMailID()==mailToFind.getMailID()){
                    index = this.currentUser.getSentFolder().getMail().indexOf(mailToFind);
                    break;
                }
            }
            this.currentUser.getSentFolder().getMail().remove(index);
        } else if (mail.getMailType().equals("draft")) {
            for (Mail mailToFind:this.currentUser.getDraftFolder().getMail()){
                if (newMail.getMailID()==mailToFind.getMailID()){
                    index = this.currentUser.getDraftFolder().getMail().indexOf(mailToFind);
                    break;
                }
            }
            this.currentUser.getDraftFolder().getMail().remove(index);
        }
        else
            return null;
        file.generateJsonFile(currentUser);
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