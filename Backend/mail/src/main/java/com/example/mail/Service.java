package com.example.mail;

public class Service {
    public User currentuser;
    private final registedUsers checker = new registedUsers();
    private final FileService file = new FileService();

    public User signup(UserDto user){
        User newUser = checker.addUser(user);
        if(newUser != null){
            file.generateJsonFile(newUser);
        }
        this.currentuser = newUser;
        return newUser;
    }
    public User signin(UserDto user){
        int id = checker.getUserId(user.getEmail());
        return null;
    }
}