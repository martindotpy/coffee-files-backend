package xyz.cupscoffee.backend.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import xyz.cupscoffee.backend.api.response.SyncResponse;
import xyz.cupscoffee.backend.api.response.SystemStatusResponse;
import xyz.cupscoffee.backend.service.api.interfaces.SavStructureExporter;
import xyz.cupscoffee.files.api.SavFileReader;
import xyz.cupscoffee.files.api.SavStructure;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

@RestController
@RequestMapping("/api/system")
@AllArgsConstructor
public class SystemController {
    private final HttpSession session;
    private final SavStructureExporter savStructureExporter;

    @PostMapping("/import")
    public ResponseEntity<Void> loadSavFile(@RequestPart("file") MultipartFile file)
            throws InvalidFormatFileException, IOException {
        SavStructure savStructure = SavFileReader.readSavFile(file.getInputStream());
        session.setAttribute("file", savStructure);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createSavFile() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportSavFile() throws InvalidFormatFileException {
        SavStructure savStructure = (SavStructure) session.getAttribute("file");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "CupsOfCoffee.sav");

        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(savStructureExporter.toBytes(savStructure));
        } catch (UnsupportedEncodingException e) {
            throw new InvalidFormatFileException("Error to export the file");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<SystemStatusResponse> getSystemStatus() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/sync")
    public ResponseEntity<SyncResponse> getSyncSystem() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
