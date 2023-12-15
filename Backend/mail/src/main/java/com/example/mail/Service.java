package com.example.mail;

@org.springframework.stereotype.Service
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
        if (id!=0) {
            User newUser = file.getJsonData(id);
            if(user.getPassword().equals(newUser.getPassword()))return newUser;
            return new User();
        }
        return new User();
    }
    public void signOut(){
        this.currentuser = null;
    }
}