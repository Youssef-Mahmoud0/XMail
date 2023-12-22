package com.example.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class Controller {
    @Autowired
    Service service = new Service();

    @GetMapping("/getUser")
    public User getUser(String email){
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
    public SentFolder addMail(@RequestBody Mail mail){
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
    public TrashFolder trashMail(@RequestBody Mail mail){
//        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.trashMail(mail);
    }
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
    @PostMapping("/restoreTrash")
    public TrashFolder restoreFromTrash(@RequestBody Mail mail){
//        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.restoreFromTrash(mail);
    }
    @PostMapping("deleteTrash")
    public TrashFolder deleteFromTrash(@RequestBody Mail mail){
//        service.setCurrentUser(service.getUser(systemDto.getEmail()));
        return service.deleteFromTrash(mail);
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