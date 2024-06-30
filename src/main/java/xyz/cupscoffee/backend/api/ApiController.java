package xyz.cupscoffee.backend.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

import jakarta.servlet.http.HttpSession;

import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;
import xyz.cupscoffee.files.api.SavFileReader;
import xyz.cupscoffee.files.api.SavStructure;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import xyz.cupscoffee.backend.api.request.CreateFileRequest;
import xyz.cupscoffee.backend.api.request.DeleteFileRequest;
import xyz.cupscoffee.backend.api.request.DownloadFileRequest;
import xyz.cupscoffee.backend.api.request.EditFileRequest;
import xyz.cupscoffee.backend.api.request.MoveFileRequest;
import xyz.cupscoffee.backend.api.request.ReadFileRequest;
import xyz.cupscoffee.backend.api.request.UploadFileRequest;
import xyz.cupscoffee.backend.api.response.ReadFileContentResponse;
import xyz.cupscoffee.backend.api.response.SyncResponse;
import xyz.cupscoffee.backend.api.response.SystemStatusResponse;
import xyz.cupscoffee.backend.api.squema.FileSquema;
import xyz.cupscoffee.backend.service.api.implementation.SavStructureExporterImpl;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {
    private final HttpSession session;
    private final SavStructureExporterImpl savStructureWriter;

    // System
    @PostMapping("/system/import")
    public ResponseEntity<Void> loadSavFile(@RequestPart("file") MultipartFile file)
            throws InvalidFormatFileException, IOException {
        SavStructure savStructure = SavFileReader.readSavFile(file.getInputStream());
        session.setAttribute("file", savStructure);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/system/create")
    public ResponseEntity<Void> createSavFile() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/system/export")
    public ResponseEntity<byte[]> exportSavFile() throws InvalidFormatFileException {
        SavStructure savStructure = (SavStructure) session.getAttribute("file");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "CupsOfCoffee.sav");

        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(savStructureWriter.toBytes(savStructure));
        } catch (UnsupportedEncodingException e) {
            throw new InvalidFormatFileException("Error to export the file");
        }
    }

    @GetMapping("/system/status")
    public ResponseEntity<SystemStatusResponse> getSystemStatus() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/system/sync")
    public ResponseEntity<SyncResponse> getSyncSystem() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping("/system/logout")
    public ResponseEntity<Void> logout() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // File
    @PatchMapping("/files/upload")
    public ResponseEntity<FileSquema> uploadFile(
            @RequestPart("file") MultipartFile file,
            UploadFileRequest uploadFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping("/files/create")
    public ResponseEntity<FileSquema> createFile(CreateFileRequest createFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/files/read")
    public ResponseEntity<ReadFileContentResponse> readFile(ReadFileRequest readFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PatchMapping("/files/edit")
    public ResponseEntity<FileSquema> editFile(EditFileRequest editFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @DeleteMapping("/files/delete")
    public ResponseEntity<Void> deleteFile(DeleteFileRequest deleteFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/files/delete")
    public ResponseEntity<byte[]> downloadFile(DownloadFileRequest downloadFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping("/files/move")
    public ResponseEntity<FileSquema> moveFile(MoveFileRequest moveFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Hello world
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }

    // Handler exception
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
