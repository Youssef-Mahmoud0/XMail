package com.example.mail;

import com.example.mail.filter.AndCriteria;
import com.example.mail.filter.ContactCriteria;
import com.example.mail.filter.SearchAllCriteria;
import com.example.mail.proxy.Xmail;
import com.example.mail.proxy.ProxyXmail;
import com.example.mail.sortStrategy.SortStrategy;
import com.example.mail.sortStrategy.SortStrategyFactory;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

@org.springframework.stereotype.Service
public class Service {
    public User currentUser;
    private final RegisteredUsers registeredUsers = new RegisteredUsers();
    private final FileService file = new FileService();

    private Xmail xmail = new ProxyXmail();

    public boolean signUp(UserDto user) throws NoSuchAlgorithmException {
        user.setPassword(Hashing.hashingPassword(user.getPassword()));
        User newUser = registeredUsers.addUser(user);
        if(newUser != null){
            file.generateJsonFile(newUser);
            this.currentUser = newUser;
            this.xmail.signIn(user.getEmail());
        }
        return newUser!=null;
    }
    public boolean signIn(UserDto user) throws NoSuchAlgorithmException {
        int id = registeredUsers.getUserId(user.getEmail());
        if (id!=0) {
            User newUser = file.getJsonData(id);
            if(Hashing.hashingPassword(user.getPassword()).equals(newUser.getPassword())) {
                this.xmail.signIn(newUser.getEmail());
                this.currentUser = newUser;
                return true;
            }
        }
        return false;
    }
    public void signOut(String email){
        this.xmail.signedOut(email);
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
        if(mail.getAttachments()!=null) {
            mailBuilder.setAttachments(mail.getAttachments());
//            System.out.println("aaa"+mailBuilder.getAttachments());
//            System.out.println("attachments "+mail.getAttachments());
        }
        if(mail.getContent() != null)
            mailBuilder.setContent(mail.getContent());
        Mail newMail = mailBuilder.build();
        this.currentUser.getSentFolder();
        this.currentUser.addSent(newMail);
//        System.out.println(this.currentUser.getSentFolder().getMail());
        file.generateJsonFile(currentUser);
//        mail.setFrom(this.currentUser.getEmail());
        this.sortUserArrays();
        SystemDto systemDto = new SystemDto();
        systemDto.setSourceMails(this.currentUser.getSentFolder().getMail());
        systemDto.setDestinationMails(this.currentUser.getDraftFolder().getMail());
        System.out.println( "from add mail"+this.currentUser.getEmail());
        mail.setMailType("inbox");
        mail.setLocalDate(LocalDate.now());
        mail.setLocalTime(LocalTime.now());
        setInbox(mail.getTo(), mail);
        //Coming soon...
//        setInbox(mail.getCc(),mail);
//        setInbox(mail.getBcc(),mail);

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
        this.sortUserArrays();
        file.generateJsonFile(currentUser);
//        mail.setFrom(this.currentUser.getEmail());
        return this.currentUser.getDraftFolder();
    }
    public SystemDto trashMail(ArrayList<Mail> mails, String source){
        SystemDto systemDto = new SystemDto();
        this.checkTrash();
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
            }else{
                for(CustomFolder folder:this.currentUser.getCustomFolders()){
                    if(folder.getFolderName().equals(mail.getMailType())){
                        folder.getMail().removeIf(mailToFind -> mailToFind.getMailID() == mail.getMailID());
                        break;
                    }
                }
            }
        }
        if (source.equals("inbox"))
            systemDto.setSourceMails(this.currentUser.getInboxFolder().getMail());
        else if (source.equals("sent"))
            systemDto.setSourceMails(this.currentUser.getSentFolder().getMail());
        else if (source.equals("draft")) {
            systemDto.setSourceMails(this.currentUser.getDraftFolder().getMail());
        }
        else{
            for(CustomFolder folder:this.currentUser.getCustomFolders()){
                if(folder.getFolderName().equals(source)){
                    systemDto.setSourceMails(folder.getMail());
                    break;
                }
            }
        }
        System.out.println(this.currentUser.getInboxFolder().getMail());
        System.out.println(systemDto.getSourceMails());
        this.sortUserArrays();
        systemDto.setDestinationMails(this.currentUser.getTrashFolder().getMail());
        file.generateJsonFile(currentUser);
        return systemDto;
    }
    public User restoreFromTrash(ArrayList<Mail> mails){
        this.checkTrash();
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
            else{
                for(CustomFolder folder:this.currentUser.getCustomFolders()){
                    if(folder.getFolderName().equals(newMail.getMailType())){
                        folder.getMail().add(newMail);
                        break;
                    }
                }
            }
            this.currentUser.getTrashFolder().getMail().removeIf(trashMail -> trashMail.getMailID() == newMail.getMailID());
        }
        this.sortUserArrays();
        file.generateJsonFile(this.currentUser);
        return this.currentUser;
    }
    public SystemDto deleteFromTrash(ArrayList<Mail> mails){
        this.checkTrash();
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
        SystemDto systemDto = new SystemDto();
        systemDto.setDestinationMails(this.currentUser.getTrashFolder().getMail());
        systemDto.setSourceMails(this.currentUser.getTrashFolder().getMail());
        System.out.println(this.currentUser.getTrashFolder().getMail());
//        return this.currentUser.getTrashFolder();
        System.out.println("Delete From Trash");
        file.generateJsonFile(this.currentUser);
        return systemDto;
    }
    public void sortUserArrays(){
        (this.currentUser.getDraftFolder().getMail()).sort(Comparator.comparing(Mail::getMailID).reversed());
        (this.currentUser.getInboxFolder().getMail()).sort(Comparator.comparing(Mail::getMailID).reversed());
        (this.currentUser.getSentFolder().getMail()).sort(Comparator.comparing(Mail::getMailID).reversed());
    }
    public void checkTrash(){
        Collections.reverse(this.currentUser.getTrashFolder().getMail());
        this.currentUser.getTrashFolder().getMail().removeIf(mail -> {
            return mail.getLocalDate().isBefore(LocalDate.now().minusDays(30)) || mail.getLocalDate().isEqual(LocalDate.now().minusDays(30));
        });
        file.generateJsonFile(this.currentUser);
    }
    public User getUser(String email){
        int id = registeredUsers.getUserId(email);
        if (id!=0) {
            this.currentUser = file.getJsonData(id);
//            user.getTrashFolder().getMail().removeIf(mail -> {
//                return mail.getLocalDate().isEqual(LocalDate.now().minusDays(30));
//            });
//            file.generateJsonFile(user);
            this.checkTrash();
            this.sortUserArrays();
            return this.currentUser;
        }
        return null;
    }
    public void setInbox(ArrayList<String> emails, Mail mail){
        for(String email:emails) {
            User user = getUser(email);
            if (user != null) {
                user.getInboxFolder();
                mail.setMailID(user.getGlobalMailNumber());
                user.addInbox(mail);
                file.generateJsonFile(user);
            }
        }
    }

    public boolean setCurrentUser(User currentUser) {
//        System.out.println(currentUser.getEmail());
        System.out.println("before fetch"+this.currentUser.getEmail());
        this.currentUser = this.xmail.checkeUser(currentUser.getEmail());
//        this.currentUser = currentUser;
        System.out.println("after fetch"+this.currentUser.getEmail());
        return this.currentUser != null;
    }
    public void sendDraft(Mail mail){
        //                int index = this.currentUser.getDraftFolder().getMail().indexOf(draft);
        this.currentUser.getDraftFolder().getMail().removeIf(draft -> draft.getMailID() == mail.getMailID());
//        mail.setMailType("sent");
//        this.addMail(mail);
//        return this.currentUser.getDraftFolder();
    }
    public ArrayList<Mail> search(HashMap<String,String> hashMap, ArrayList<Mail> mails){
        SearchAllCriteria searchAllCriteria = new SearchAllCriteria();
        return searchAllCriteria.matchesCriteria(mails, hashMap);
    }
    public ArrayList<Mail> filter(HashMap<String,String> hashMap, ArrayList<Mail> mails){
        AndCriteria andCriteria = new AndCriteria();
        return andCriteria.meetCriteria(mails, hashMap);
    }
    public ArrayList<CustomFolder> createCustomFolder(String folderName){
        this.currentUser.getCustomFolders();
        this.currentUser.createCustomFolder(folderName);
        this.file.generateJsonFile(this.currentUser);
        return this.currentUser.getCustomFolders();
    }
    public void bulkMove(Mail mail){
        if(mail.getMailType().equals("inbox")){
            this.currentUser.getInboxFolder().getMail().removeIf(mailToFind -> mailToFind.getMailID() == mail.getMailID());
        } else if (mail.getMailType().equals("sent")){
            this.currentUser.getSentFolder().getMail().removeIf(mailToFind -> mailToFind.getMailID() == mail.getMailID());
        } else if (mail.getMailType().equals("draft")) {
            this.currentUser.getDraftFolder().getMail().removeIf(mailToFind -> mailToFind.getMailID() == mail.getMailID());
        }
        else{
            for(CustomFolder folder:this.currentUser.getCustomFolders()){
                if(folder.getFolderName().equals(mail.getMailType())){
                    folder.getMail().removeIf(mailToFind -> mailToFind.getMailID() == mail.getMailID());
                    break;
                }
            }
        }
    }
    public SystemDto addToCustom(String folderName, ArrayList<Mail> mails){
//        ArrayList<CustomFolder> customFolders = this.currentUser.getCustomFolders();
        SystemDto systemDto = new SystemDto();
        if(mails.get(0).getMailType().equals("inbox")) {
            systemDto.setSourceMails(this.currentUser.getInboxFolder().getMail());
        }
        if(mails.get(0).getMailType().equals("sent")) {
            systemDto.setSourceMails(this.currentUser.getSentFolder().getMail());
        }
        if(mails.get(0).getMailType().equals("draft")) {
            systemDto.setSourceMails(this.currentUser.getDraftFolder().getMail());
        }
        else{
            for(CustomFolder folder: this.currentUser.getCustomFolders()) {
                if (folder.getFolderName().equals(mails.get(0).getMailType())) {
                    System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEES folders done");
                    systemDto.setSourceMails(folder.getMail());
                    System.out.println(systemDto.getSourceMails());
                }
            }
        }
        if(systemDto.getSourceMails() == null){
            systemDto.setSourceMails(new ArrayList<Mail>());
        }
        for(CustomFolder folder:this.currentUser.getCustomFolders()){
            if(folder.getFolderName().equals(folderName)){
                for(Mail mail:mails){
                    this.bulkMove(mail);
                    mail.setMailType(folderName);
                    mail.setMailID(this.currentUser.getGlobalMailNumber());
                    folder.addMail(mail);
                }
                systemDto.setDestinationMails(folder.getMail());
                break;
            }
        }
        this.currentUser.setCustomFolders(this.currentUser.getCustomFolders());
        this.file.generateJsonFile(this.currentUser);
        return systemDto;
//        return this.currentUser.getCustomFolders();
    }
    public ArrayList<CustomFolder> removeFolder(String folderName){
        this.currentUser.getCustomFolders().removeIf(folder -> folder.getFolderName().equals(folderName));
        this.file.generateJsonFile(this.currentUser);
        return this.currentUser.getCustomFolders();
    }
    public ArrayList<CustomFolder> renameFolder(String oldName, String newName){
        for(CustomFolder folder:this.currentUser.getCustomFolders()){
            if(folder.getFolderName().equals(oldName)){
                folder.setFolderName(newName);
                break;
            }
        }
        this.file.generateJsonFile(this.currentUser);
        return this.currentUser.getCustomFolders();
    }
    public ArrayList<Contact> addContact(Contact contact){
        //3rfa enha doesn't make sense bas 3mlaha bec of serialization
        Contact newContact = new Contact();
        newContact.setName(contact.getName());
        newContact.setEmails(contact.getEmails());
        this.currentUser.addContact(newContact);
        file.generateJsonFile(currentUser);
        return this.currentUser.getContacts();
    }
    public ArrayList<Contact> editContact(SystemDto systemDto){
        //if didn't work do as serialization
        Contact newContact = new Contact();
        newContact.setName(systemDto.getContact().getName());
        newContact.setEmails(systemDto.getContact().getEmails());
        for (Contact contactToFind : this.currentUser.getContacts()){
            if (contactToFind.getName().equals(systemDto.getSource())){
                contactToFind.setEmails(newContact.getEmails());
                contactToFind.setName(systemDto.getDestination());
                System.out.println("hii");
                break;
            }
        }
        file.generateJsonFile(currentUser);
        return this.currentUser.getContacts();
    }
    public ArrayList<Contact> deleteContact(SystemDto systemDto){
        this.currentUser.getContacts().removeIf(contact -> contact.getName().equals(systemDto.getSource()));
        file.generateJsonFile(currentUser);
        return this.currentUser.getContacts();
    }
    public ArrayList<Contact> searchContacts(SystemDto systemDto){
        ContactCriteria contactCriteria = new ContactCriteria();
        return contactCriteria.meetCriteria(this.currentUser.getContacts(),systemDto.getSource());
    }
//    public int compare(Contact contact1, Contact contact2) {
//        return contact2.getName().compareTo(contact1.getName());
//    }
    public ArrayList<Contact> sortContacts(SystemDto systemDto){
        if(systemDto.getSource().equals("default"))return this.currentUser.getContacts();
        ArrayList<Contact> sortedContacts = new ArrayList<>(this.currentUser.getContacts());
        Collections.sort(sortedContacts, Comparator.comparing(Contact::getName));
        return sortedContacts;
    }
    public ArrayList<Mail>defaultOrPriority(SystemDto systemDto){
        SortStrategyFactory sortStrategyFactory = new SortStrategyFactory();
        SortStrategy sortStrategy = sortStrategyFactory.createStrategy(systemDto.getSource());
        return sortStrategy.sort(systemDto.getSourceMails());
    }
}