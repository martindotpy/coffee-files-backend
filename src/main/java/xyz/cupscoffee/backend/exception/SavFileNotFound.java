package xyz.cupscoffee.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SavFileNotFound extends RuntimeException {
    public SavFileNotFound(String message) {
        super(message);
    }
}
