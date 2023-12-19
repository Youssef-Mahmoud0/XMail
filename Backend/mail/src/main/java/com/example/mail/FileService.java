package com.example.mail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.paulschwarz.springdotenv.DotenvPropertyLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

interface FileGenerator{
    void generateJsonFile(User user);
    User getJsonData(int userID);
    void generateRegisteredJsonFile(RegisteredUsers registeredUsers);
    HashMap<String, Integer> getRegisteredJsonFile();
}

public class FileService implements FileGenerator{
    private final ObjectMapper objectMapper = new ObjectMapper();

    DotenvPropertyLoader dpl = new DotenvPropertyLoader();

    private final String userPath = (String) dpl.getValue("userPath");

    private final String registeredPath = (String) dpl.getValue("registeredPath");

    public String generatePath(int userID){
        return this.userPath +"\\user" +userID +".json";
    }

    @Override
    public void generateJsonFile(User user) {
        try {
            user.setFilePath(generatePath(user.getUserID()));
            objectMapper.writeValue(new File(generatePath(user.getUserID())),user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getJsonData(int userID) {
        try {
            return objectMapper.readValue(new File(generatePath(userID)), new TypeReference<User>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void generateRegisteredJsonFile(RegisteredUsers registeredUsers) {
        try {
            objectMapper.writeValue(new File(this.registeredPath),registeredUsers.getCurrentUsers());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<String, Integer> getRegisteredJsonFile() {
        try {
            return objectMapper.readValue(new File(this.registeredPath), new TypeReference<HashMap<String, Integer>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
