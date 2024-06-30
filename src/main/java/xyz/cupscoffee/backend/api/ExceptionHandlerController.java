package xyz.cupscoffee.backend.api;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

/**
 * This controller catch all the exceptions on the other controllers
 */
@RestController
public class ExceptionHandlerController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatFileException.class)
    public String handleInvalidFormatFileException() {
        return "The file does not have a valid format";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException e) {
        return "Error to load the file: " + e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(UnsupportedOperationException.class)
    public String handleUnsupportedOperationException(UnsupportedOperationException e) {
        return e.getMessage();
    }
}
