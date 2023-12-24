package com.example.mail;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@org.springframework.stereotype.Service
public class Service {
    public User currentUser;
    private final RegisteredUsers registeredUsers = new RegisteredUsers();
    private final FileService file = new FileService();

    public boolean signUp(UserDto user) throws NoSuchAlgorithmException {
        user.setPassword(Hashing.hashingPassword(user.getPassword()));
        User newUser = registeredUsers.addUser(user);
        if(newUser != null){
            file.generateJsonFile(newUser);
        }
//        this.currentUser = newUser;
        return this.currentUser != null;
//        return newUser;
    }
    public boolean signIn(UserDto user) throws NoSuchAlgorithmException {
        int id = registeredUsers.getUserId(user.getEmail());
        if (id!=0) {
            User newUser = file.getJsonData(id);
            if(Hashing.hashingPassword(user.getPassword()).equals(newUser.getPassword())) {
                this.currentUser = newUser;
                return true;
            }
        }
        return false;
    }
    public void signOut(){
        this.currentUser = null;
    }
    public SystemDto addMail(Mail mail){
        System.out.println("YES YES NEW MAIIIIIIIIIIL !!!!!!!!!!");
        if(mail.getMailType().equals("draft")){
            this.sendDraft(mail);
            mail.setMailType("sent");
        }
        MailBuilder mailBuilder = new MailBuilder();
        mailBuilder.setTo(mail.getTo()).setMailID(this.currentUser.getGlobalMailNumber()).setMailType(mail.getMailType()).setLocalDate().setLocalTime();
        mailBuilder.setPriority(mail.getPriority()).setPriority(mail.getPriority()).setFrom(mail.getFrom()).setSubject(mail.getSubject());
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
        SystemDto systemDto = new SystemDto();
        systemDto.setSourceMails(this.currentUser.getSentFolder().getMail());
        systemDto.setDestinationMails(this.currentUser.getDraftFolder().getMail());
        return systemDto;
    }
    public DraftFolder draftMail(Mail mail){
        System.out.println("Drafted hehe");
        System.out.println(mail.getMailID());
        if(mail.getMailID() != -1){
            //                    int index = this.currentUser.getDraftFolder().getMail().indexOf(draft);
            this.currentUser.getDraftFolder().getMail().removeIf(draft -> draft.getMailID() == mail.getMailID());
        }
        MailBuilder mailBuilder = new MailBuilder();
        mailBuilder.setTo(mail.getTo()).setMailID(this.currentUser.getGlobalMailNumber()).setMailType(mail.getMailType()).setLocalDate().setLocalTime();
        mailBuilder.setPriority(mail.getPriority()).setPriority(mail.getPriority()).setFrom(mail.getFrom()).setSubject(mail.getSubject());
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
    public SystemDto trashMail(ArrayList<Mail> mails, String source){
        SystemDto systemDto = new SystemDto();
        for (Mail mail : mails){
            System.out.println("TRASH WORKED LESGOOOO");
            MailBuilder mailBuilder = new MailBuilder();
            mailBuilder.setTo(mail.getTo()).setMailID(mail.getMailID()).setMailType(mail.getMailType());
            mailBuilder.setPriority(mail.getPriority()).setPriority(mail.getPriority()).setFrom(mail.getFrom()).setSubject(mail.getSubject());
            mailBuilder.setOldLocalDate(mail.getLocalDate()).setOldLocalTime(mail.getLocalTime());
            if(mail.getAttachments()!=null)
                mailBuilder.setAttachments(mail.getAttachments());
            if(mail.getContent() != null)
                mailBuilder.setContent(mail.getContent());
            Mail newMail = mailBuilder.build();
            this.currentUser.setTrashFolder(this.currentUser.getTrashFolder());
            this.currentUser.addTrash(newMail);
            System.out.println(mail.getMailType());
            if(mail.getMailType().equals("inbox")){
                this.currentUser.getInboxFolder().getMail().removeIf(mailToFind -> mailToFind.getMailID() == newMail.getMailID());
            } else if (mail.getMailType().equals("sent")){
                this.currentUser.getSentFolder().getMail().removeIf(mailToFind -> mailToFind.getMailID() == newMail.getMailID());
            } else if (mail.getMailType().equals("draft")) {
                this.currentUser.getDraftFolder().getMail().removeIf(mailToFind -> mailToFind.getMailID() == newMail.getMailID());
            }
            else
                return null;
        }
        if (source.equals("inbox"))
            systemDto.setSourceMails(this.currentUser.getInboxFolder().getMail());
        else if (source.equals("sent"))
            systemDto.setSourceMails(this.currentUser.getSentFolder().getMail());
        else if (source.equals("draft")) {
            systemDto.setSourceMails(this.currentUser.getDraftFolder().getMail());
        }
        systemDto.setDestinationMails(this.currentUser.getTrashFolder().getMail());
        file.generateJsonFile(currentUser);
        return systemDto;
    }
    public User restoreFromTrash(ArrayList<Mail> mails){
        for (Mail mail : mails){
            MailBuilder mailBuilder = new MailBuilder();
            mailBuilder.setTo(mail.getTo()).setMailID(mail.getMailID()).setMailType(mail.getMailType());
            mailBuilder.setPriority(mail.getPriority()).setPriority(mail.getPriority()).setFrom(mail.getFrom()).setSubject(mail.getSubject());
            mailBuilder.setOldLocalDate(mail.getLocalDate()).setOldLocalTime(mail.getLocalTime());
            if(mail.getAttachments()!=null)
                mailBuilder.setAttachments(mail.getAttachments());
            if(mail.getContent() != null)
                mailBuilder.setContent(mail.getContent());
            Mail newMail = mailBuilder.build();
            if (newMail.getMailType().equals("inbox")){
                this.currentUser.addInbox(newMail);
            }else if (newMail.getMailType().equals("sent")){
                this.currentUser.addSent(newMail);
            }else if (newMail.getMailType().equals("draft")){
                this.currentUser.addDraft(newMail);
            }
            this.currentUser.getTrashFolder().getMail().removeIf(trashMail -> trashMail.getMailID() == newMail.getMailID());
        }
        return this.currentUser;
    }
    public TrashFolder deleteFromTrash(ArrayList<Mail> mails){
        for (Mail mail : mails){
            MailBuilder mailBuilder = new MailBuilder();
            mailBuilder.setTo(mail.getTo()).setMailID(mail.getMailID()).setMailType(mail.getMailType());
            mailBuilder.setPriority(mail.getPriority()).setPriority(mail.getPriority()).setFrom(mail.getFrom()).setSubject(mail.getSubject());
            mailBuilder.setOldLocalDate(mail.getLocalDate()).setOldLocalTime(mail.getLocalTime());
            if(mail.getAttachments()!=null)
                mailBuilder.setAttachments(mail.getAttachments());
            if(mail.getContent() != null)
                mailBuilder.setContent(mail.getContent());
            Mail newMail = mailBuilder.build();
            this.currentUser.getTrashFolder().getMail().removeIf(trashMail -> trashMail.getMailID() == newMail.getMailID());
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
    public void sendDraft(Mail mail){
        //                int index = this.currentUser.getDraftFolder().getMail().indexOf(draft);
        this.currentUser.getDraftFolder().getMail().removeIf(draft -> draft.getMailID() == mail.getMailID());
//        mail.setMailType("sent");
//        this.addMail(mail);
//        return this.currentUser.getDraftFolder();
    }
}