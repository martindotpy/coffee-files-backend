package xyz.cupscoffee.backend.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import xyz.cupscoffee.backend.exception.InvalidPathException;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

/**
 * This controller catch all the exceptions on the other controllers
 */
@Controller
public class ExceptionHandlerController {
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The file does not have a valid format")
    @ExceptionHandler(InvalidFormatFileException.class)
    public String handleInvalidFormatFileException() {
        return "The file does not have a valid format";
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error to load the file: {e.getMessage()}")
    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException e) {
        return "Error to load the file: " + e.getMessage();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error with the encoding to export the file: {e.getMessage()}")
    @ExceptionHandler(UnsupportedEncodingException.class)
    public String handleUnsupportedEncodingException() {
        return "Error with the encoding to export the file";
    }

    @ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED, reason = "{e.getMessage()}")
    @ExceptionHandler(UnsupportedOperationException.class)
    public String handleUnsupportedOperationException(UnsupportedOperationException e) {
        return e.getMessage();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid path: {e.getMessage()}")
    @ExceptionHandler(InvalidPathException.class)
    public String handleInvalidPathException(InvalidPathException e) {
        return e.getMessage();
    }
}
