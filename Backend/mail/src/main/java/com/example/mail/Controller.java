package com.example.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class Controller {
    @Autowired
    Service service = new Service();

    @PostMapping("/signup")
    public User sigup(@RequestBody UserDto user){
        return service.signup(user);
    }
    public User signin(@RequestBody UserDto user){
        return service.signin(user);
    }
}
