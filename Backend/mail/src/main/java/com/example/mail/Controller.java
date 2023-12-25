package com.example.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class Controller {
    @Autowired
    Service service = new Service();

    @PostMapping("/getUser")
    public User getUser(@RequestBody String email){
        service.setCurrentUser(service.getUser(email));
        return service.getUser(email);
    }
    @PostMapping("/signUp")
    public boolean signUp(@RequestBody UserDto user) throws NoSuchAlgorithmException {
        System.out.println("Aaaaaaaaaaaaah");
        return service.signUp(user);
    }
    @PostMapping("/signIn")
    public boolean signIn(@RequestBody UserDto user) throws NoSuchAlgorithmException {
        return service.signIn(user);
    }
    @GetMapping("/signOut")
    public void signOut(){
        service.signOut();
    }

    @PostMapping("/addMail")
    public SystemDto addMail(@RequestBody Mail mail){
        service.setCurrentUser(service.getUser(mail.getFrom()));
        return service.addMail(mail);
    }
    @PostMapping("/draftMail")
    public DraftFolder draftMail(@RequestBody Mail mail){
        service.setCurrentUser(service.getUser(mail.getFrom()));
        return service.draftMail(mail);
    }
//    what to return??
//    @PostMapping("/postDraft")
//    public DraftFolder draftMail(@RequestBody Mail mail){
//        service.setCurrentUser(service.getUser(mail.getFrom()));
//        return service.draftMail(mail);
//    }

//    @PostMapping("/trashMail")
//    public TrashFolder trashMail(@RequestBody SystemDto systemDto){
//        service.setCurrentUser(service.getUser(systemDto.getEmail()));
//        return service.trashMail(systemDto.getMail());
//    }
    @PostMapping("/trashMail")
    public SystemDto trashMail(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.trashMail(systemDto.getSourceMails(),systemDto.getSource());
    }
    @PostMapping("/restoreTrash")
    public User restoreFromTrash(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.restoreFromTrash(systemDto.getSourceMails());
    }
//    @PostMapping("deleteTrash")
//    public TrashFolder deleteFromTrash(@RequestBody SystemDto systemDto){
//        service.setCurrentUser(service.getUser(systemDto.getEmail()));
//        return service.deleteFromTrash(systemDto.getSourceMails());
//    }
    @PostMapping("/sentFolder")
    public SentFolder getSentFolder(@RequestBody String email){
        service.setCurrentUser(service.getUser(email));
        return service.currentUser.getSentFolder();
    }
    @PostMapping("/inboxFolder")
    public InboxFolder getInboxFolder(@RequestBody String email){
        service.setCurrentUser(service.getUser(email));
        return service.currentUser.getInboxFolder();
    }
    @PostMapping("/trashFolder")
    public TrashFolder getTrashFolder(@RequestBody String email){
        service.setCurrentUser(service.getUser(email));
        return service.currentUser.getTrashFolder();
    }
    @PostMapping("/search")
    public ArrayList<Mail> search(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.search(systemDto.getHashMap(), systemDto.getSourceMails());
    }
    @PostMapping("/filter")
    public ArrayList<Mail> filter(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.filter(systemDto.getHashMap(), systemDto.getSourceMails());
    }
    @PostMapping("/addContact")
    public ArrayList<Contact> addContact(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.addContact(systemDto.getContact());
    }
    @PostMapping("/editContact")
    public ArrayList<Contact> editContact(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.editContact(systemDto);
    }
    @PostMapping("/deleteContact")
    public ArrayList<Contact> deleteContact(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.deleteContact(systemDto);
    }
    //only need email of current user and source = search written bas
    @PostMapping("/searchContacts")
    public ArrayList<Contact> searchContacts(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.searchContacts(systemDto);
    }
    @PostMapping("sortContacts")
    public ArrayList<Contact> sortContacts(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.sortContacts();
    }
    /*

        CUSTOM FOLDERS

    */
    @PostMapping("/createFolder")
    public ArrayList<CustomFolder> createFolder(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.createCustomFolder(systemDto.getSource());
    }
    @PostMapping("/renameFolder")
    public ArrayList<CustomFolder> renameFolder(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.renameFolder(systemDto.getSource(), systemDto.getDestination());
    }
    @PostMapping("/removeFolder")
    public ArrayList<CustomFolder> removeFolder(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.removeFolder(systemDto.getSource());
    }
    @PostMapping("/moveToFolder")
    public ArrayList<CustomFolder> moveToFolder(@RequestBody SystemDto systemDto){
        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.addToCustom(systemDto.getDestination(), systemDto.getSourceMails());
    }
//    @PostMapping("/restoreTrash")
//    public TrashFolder restoreFromTrash(@RequestBody SystemDto systemDto){
//        service.setCurrentUser(service.getUser(systemDto.getEmail()));
//        return service.restoreFromTrash(systemDto.getMail());
//    }
//    @PostMapping("deleteTrash")
//    public TrashFolder deleteFromTrash(@RequestBody SystemDto systemDto){
//        service.setCurrentUser(service.getUser(systemDto.getEmail()));
//        return service.deleteFromTrash(systemDto.getMail());
//    }
}