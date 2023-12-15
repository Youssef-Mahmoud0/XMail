package com.example.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class Controller {
    @Autowired
    Service service = new Service();

    @PostMapping("/signUp")
    public User signUp(@RequestBody UserDto user){
        return service.signUp(user);
    }
    @PostMapping("/signIn")
    public User signIn(@RequestBody UserDto user){
        return service.signIn(user);
    }
}
