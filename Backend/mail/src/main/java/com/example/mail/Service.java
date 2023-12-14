package com.example.mail;

public class Service {
    public User currentuser;
    public void login(User user){
        this.currentuser = user;
    }
//    public void addMail(MailParent mail, String type){
//        FolderFactory factory = new FolderFactory();
//        if(type.equals("inbox")){
//            if(currentuser.getInbox() == null){
//                factory.createFolder(type, mail);
//            }
//            else{
//                currentuser.addInbox(mail);
//            }
//        }
//        if(type.equals("sent")){
//            if(currentuser.getSent() == null){
//                factory.createFolder(type, mail);
//            }
//            else{
//                currentuser.addSent(mail);
//            }
//        }
//        if(type.equals("draft")){
//            if(currentuser.getDraft() == null){
//                factory.createFolder(type, mail);
//            }
//            else{
//                currentuser.addDraft(mail);
//            }
//        }
//
//    }
}