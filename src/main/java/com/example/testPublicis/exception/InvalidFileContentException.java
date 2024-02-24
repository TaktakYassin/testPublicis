package com.example.testPublicis.exception;

public class InvalidFileContentException extends Exception {

    public InvalidFileContentException() {
        super("The file is empty or its content is invalid");
    }

    public InvalidFileContentException(String message) {
        super(message);
    }

}
