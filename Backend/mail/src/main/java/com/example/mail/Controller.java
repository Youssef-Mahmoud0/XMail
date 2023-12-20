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

    @PostMapping("/signUp")
    public User signUp(@RequestBody UserDto user) throws NoSuchAlgorithmException {
        System.out.println("Aaaaaaaaaaaaah");
        return service.signUp(user);
    }
    @PostMapping("/signIn")
    public User signIn(@RequestBody UserDto user) throws NoSuchAlgorithmException {
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
    @PostMapping("/trashMail")
    public TrashFolder trashMail(@RequestBody Mail mail){
        service.setCurrentUser(service.getUser(mail.getFrom()));
        return service.trashMail(mail);
    }
    @GetMapping("/sentFolder")
    public SentFolder getSentFolder(@RequestBody String email){
        service.setCurrentUser(service.getUser(email));
        return service.currentUser.getSentFolder();
    }
    @GetMapping("/inboxFolder")
    public InboxFolder getInboxFolder(@RequestBody String email){
        service.setCurrentUser(service.getUser(email));
        return service.currentUser.getInboxFolder();
    }
    @GetMapping("/trashFolder")
    public TrashFolder getTrashFolder(@RequestBody String email){
        service.setCurrentUser(service.getUser(email));
        return service.currentUser.getTrashFolder();
    }
}