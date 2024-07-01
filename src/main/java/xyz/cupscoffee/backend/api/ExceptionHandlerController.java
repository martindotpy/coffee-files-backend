package xyz.cupscoffee.backend.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import xyz.cupscoffee.backend.exception.*;

import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

/**
 * This controller catch all the exceptions on the other controllers
 */
@ControllerAdvice
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SavFileNotFound.class)
    public String handleSavFileNotFound() {
        return "Sav file not found";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsupportedEncodingException.class)
    public String handleUnsupportedEncodingException() {
        return "Error with the encoding to export the file";
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(UnsupportedOperationException.class)
    public String handleUnsupportedOperationException(UnsupportedOperationException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPathException.class)
    public String handleInvalidPathException(InvalidPathException e) {
        return e.getMessage();
    }
}
