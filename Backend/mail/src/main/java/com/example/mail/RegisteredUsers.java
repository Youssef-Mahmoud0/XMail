package com.example.mail;
import java.util.HashMap;

public class RegisteredUsers {
    private HashMap<String, Integer> currentUsers;

    public User addUser(UserDto user){
        if(this.currentUsers.containsKey(user.getEmail())){
            return null;
        }
        else{
            this.currentUsers.put(user.getEmail(), this.currentUsers.size()+1);
            User newUser = new User(user, this.currentUsers.size());
            return newUser;
        }
    }
    public int getUserId(String email){
        return this.currentUsers.getOrDefault(email, 0);
    }

}
