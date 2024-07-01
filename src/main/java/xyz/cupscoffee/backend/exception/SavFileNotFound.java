package xyz.cupscoffee.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Sav file not found")
public class SavFileNotFound extends RuntimeException {
    public SavFileNotFound(String message) {
        super(message);
    }
}
