package com.example.Note.Book.exception;

public class UsernameUniqueException extends RuntimeException{

    public UsernameUniqueException(String username){
        super(String.format("Username %s already exist, try another one", username));
    }

}
