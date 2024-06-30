package xyz.cupscoffee.backend.api;

import java.io.FileNotFoundException;
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

import lombok.AllArgsConstructor;
import xyz.cupscoffee.backend.api.response.SyncResponse;
import xyz.cupscoffee.backend.api.response.SystemStatusResponse;
import xyz.cupscoffee.backend.api.squema.SavSquema;
import xyz.cupscoffee.backend.service.api.interfaces.SystemService;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

@RestController
@RequestMapping("/api/system")
@AllArgsConstructor
public class SystemController {
    private final SystemService systemService;

    @PostMapping("/import")
    public ResponseEntity<Void> loadSavFile(@RequestPart("file") MultipartFile file)
            throws InvalidFormatFileException, IOException {
        systemService.importSavStructure(file.getInputStream());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<SavSquema> createSavFile() throws FileNotFoundException, IOException {
        SavSquema savSquema = SavSquema.from(systemService.createDefaultSavStructure());

        return ResponseEntity.ok(savSquema);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportSavFile() throws InvalidFormatFileException, UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "CupsOfCoffee.sav");

        return ResponseEntity.ok()
                .headers(headers)
                .body(systemService.export());

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
