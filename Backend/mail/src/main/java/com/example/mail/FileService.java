package com.example.mail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

interface FileGenerator{
    void generateJsonFile(User user);
    User getJsonData(int userID);
}

public class FileService implements FileGenerator{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String userPath = "M:\\FOE AU\\CSE 2\\first semster\\Programming 2\\labs\\lab4\\Backend\\mail\\users\\user";
    public String generatePath(int userID){
        return this.userPath + userID +".json";
    }

    @Override
    public void generateJsonFile(User user) {
        try {
            objectMapper.writeValue(new File(generatePath(user.getUserID())),user);
            user.setFilePath(generatePath(user.getUserID()));
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
}
