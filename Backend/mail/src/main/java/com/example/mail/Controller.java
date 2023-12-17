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
}
