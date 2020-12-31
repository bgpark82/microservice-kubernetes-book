package se.magnus.util.exception;

import lombok.NoArgsConstructor;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message) {
        super(message);
    }
}
