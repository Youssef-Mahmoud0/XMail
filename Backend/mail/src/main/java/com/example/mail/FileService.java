package com.example.mail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

interface FileGenerator{
    void generateJsonFile(User user);
    User getJsonData(User user);
}

public class FileService implements FileGenerator{
    private ObjectMapper objectMapper = new ObjectMapper();
    private String userPath = "D:\\Rowan's CSE\\Term 5\\OOP\\XMail\\Backend\\mail\\user";
    public String generatePath(User user){
        return this.userPath + String.valueOf(user.getUserID())+".json";
    }

    @Override
    public void generateJsonFile(User user) {
        try {
            objectMapper.writeValue(new File(generatePath(user)),user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getJsonData(User user) {
        try {
            return objectMapper.readValue(new File(generatePath(user)), new TypeReference<User>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
