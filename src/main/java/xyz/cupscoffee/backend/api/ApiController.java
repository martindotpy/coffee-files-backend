package xyz.cupscoffee.backend.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import xyz.cupscoffee.files.api.SavFileReader;
import xyz.cupscoffee.files.api.SavStructure;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

import xyz.cupscoffee.backend.service.api.implementation.SavStructureExporterImpl;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {
    private final HttpSession session;
    private final SavStructureExporterImpl savStructureWriter;

    @PostMapping("/load")
    public ResponseEntity<?> loadSavFile(@RequestPart("file") MultipartFile file, HttpServletRequest request)
            throws InvalidFormatFileException, IOException {
        HttpSession session = request.getSession();

        SavStructure savStructure = SavFileReader.readSavFile(file.getInputStream());
        session.setAttribute("file", savStructure);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportSavFile() {
        SavStructure savStructure = (SavStructure) session.getAttribute("file");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "CupsOfCoffee.sav");

        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(savStructureWriter.toBytes(savStructure));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

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

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
