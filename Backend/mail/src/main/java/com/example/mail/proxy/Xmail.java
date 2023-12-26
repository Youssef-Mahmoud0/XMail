package com.example.mail.proxy;

import com.example.mail.User;

public interface Xmail {
    public User checkeUser(String email);
    public void signedOut(String email);
    public void signIn(String email);
}
