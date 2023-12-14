package com.example.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class Controller {
//    @Autowired
//    private ArrayList<User>users;
    @PostMapping("/signup")
    public boolean signed(@RequestBody User user){
        return true;
    }
}
