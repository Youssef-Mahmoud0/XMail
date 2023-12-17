package com.example.mail;

import java.security.NoSuchAlgorithmException;

@org.springframework.stereotype.Service
public class Service {
    public User currentuser;
    private final RegisteredUsers registeredUsers = new RegisteredUsers();
    private final FileService file = new FileService();

    public User signUp(UserDto user) throws NoSuchAlgorithmException {
        user.setPassword(Hashing.hashingPassword(user.getPassword()));
        User newUser = registeredUsers.addUser(user);
        if(newUser != null){
            file.generateJsonFile(newUser);
        }
        this.currentuser = newUser;
        return newUser;
    }
    public User signIn(UserDto user) throws NoSuchAlgorithmException {
        int id = registeredUsers.getUserId(user.getEmail());
        if (id!=0) {
            User newUser = file.getJsonData(id);
            if(Hashing.hashingPassword(user.getPassword()).equals(newUser.getPassword()))return newUser;
            return new User();
        }
        return new User();
    }
    public void signOut(){
        this.currentuser = null;
    }
}