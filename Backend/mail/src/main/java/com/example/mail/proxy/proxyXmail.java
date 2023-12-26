package com.example.mail.proxy;
import com.example.mail.User;

import java.util.ArrayList;

public class proxyXmail implements Xmail {
    Xmail xmail = new realXmail();
    ArrayList<String> signedInUsers = new ArrayList<>();
    @Override
    public User checkeUser(String email){
        if(signedInUsers.contains(email)) {
            System.out.println(email);
            System.out.println(this.signedInUsers);
            return xmail.checkeUser(email);
        }
        return null;
    }
    @Override
    public void signIn(String email){
        System.out.println("hello from proxy");
        this.signedInUsers.add(email);
        System.out.println(this.signedInUsers);
    }
    @Override
    public void signedOut(String email){
        System.out.println(this.signedInUsers);
        this.signedInUsers.remove(email);
        System.out.println(this.signedInUsers);
    }
}
