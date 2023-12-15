package com.example.mail;
import java.util.HashMap;

public class RegisteredUsers {
    private HashMap<String, Integer> currentUsers = new HashMap<String,Integer>();
    private FileService fileService = new FileService();

    public RegisteredUsers(){
        try{
            this.currentUsers = fileService.getRegisteredJsonFile();
        } catch (Exception e) {
            currentUsers = new HashMap<>();
            fileService.generateRegisteredJsonFile(this);
        }
    }

    public User addUser(UserDto user){
        if(this.currentUsers == null){
            currentUsers = new HashMap<>();
            currentUsers.put(user.getEmail(),1);
            fileService.generateRegisteredJsonFile(this);
            User newUser = new User(user, this.currentUsers.size());
            return newUser;
        }
        else if( this.currentUsers.containsKey(user.getEmail())){
            return null;
        }
        else{
            this.currentUsers.put(user.getEmail(), this.currentUsers.size()+1);
            User newUser = new User(user, this.currentUsers.size());
            fileService.generateRegisteredJsonFile(this);
            return newUser;
        }
    }
    public int getUserId(String email){
        return this.currentUsers.getOrDefault(email, 0);
    }

    public HashMap<String, Integer> getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(HashMap<String, Integer> currentUsers) {
        this.currentUsers = currentUsers;
    }
}
