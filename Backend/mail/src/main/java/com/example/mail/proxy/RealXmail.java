package com.example.mail.proxy;

import com.example.mail.FileService;
import com.example.mail.RegisteredUsers;
import com.example.mail.User;

public class RealXmail implements Xmail {
//    RegisteredUsers registeredUsers = new RegisteredUsers();
    @Override
    public User checkeUser(String email){
        RegisteredUsers registeredUsers = new RegisteredUsers();
        System.out.println(registeredUsers);
        System.out.println("this email sent to checkuser " + email);
        int id = registeredUsers.getUserId(email);
        System.out.println(id);
        if (id!=0) {
            System.out.println("Hello from realXmail");
//            System.out.println(new FileService().getJsonData(id));
            return new FileService().getJsonData(id);
        }
        return null;
    }
    @Override
    public void signedOut(String email){}
    @Override
    public void signIn(String email){}
}
