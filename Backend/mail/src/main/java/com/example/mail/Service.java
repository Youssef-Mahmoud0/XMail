package com.example.mail;

public class Service {
    public User currentuser;
    private final RegisteredUsers registeredUsers = new RegisteredUsers();
    private final FileService file = new FileService();

    public User signUp(UserDto user){
        User newUser = registeredUsers.addUser(user);
        if(newUser != null){
            file.generateJsonFile(newUser);
        }
        this.currentuser = newUser;
        return newUser;
    }
    public User signIn(UserDto user){
        int id = registeredUsers.getUserId(user.getEmail());
        if (id!=0)
            return file.getJsonData(id);
        return null;
    }
}