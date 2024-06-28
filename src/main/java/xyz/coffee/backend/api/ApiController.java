package xyz.coffee.backend.api;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import xyz.coffee.backend.service.api.implementation.SavStructureExporterImpl;
import xyz.cupscoffee.files.api.SavFileReader;
import xyz.cupscoffee.files.api.SavStructure;
import xyz.cupscoffee.files.api.implementation.SimpleDisk;
import xyz.cupscoffee.files.api.implementation.SimpleFile;
import xyz.cupscoffee.files.api.implementation.SimpleFolder;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {
    private final HttpSession session;
    private final SavStructureExporterImpl savStructureWriter;

    @PostMapping("/load")
    public ResponseEntity<?> loadSavFile(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SavFileReader savFileReader = new SavFileReader();

        session.setAttribute("file", new SimpleSavStructure(
                "CupsOfCoffee",
                new SimpleDisk[1],
                new java.util.HashMap<>()));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportSavFile() {
        SavStructure savStructure = (SavStructure) session.getAttribute("file");
        String fileContent = "console.log('Hello, World!');\n";
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedFileContent = encoder.encodeToString(fileContent.getBytes());
        ByteBuffer fileContentBuffer = ByteBuffer.wrap(encodedFileContent.getBytes());
        Map<String, String> metadata = new java.util.HashMap<>();
        metadata.put("author", "Elder");

        SimpleFile simpleFile = new SimpleFile("file.js",
                fileContentBuffer,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\directory\\other_directory\\file.js"),
                metadata);
        SimpleFolder simpleFolder1 = new SimpleFolder("other_directory",
                List.of(simpleFile),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\directory\\other_directory"),
                metadata);
        SimpleFolder simpleFolder2 = new SimpleFolder("directory",
                new java.util.LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\directory\\directory"),
                metadata);
        SimpleFolder simpleFolder3 = new SimpleFolder("directory",
                new java.util.LinkedList<>(),
                List.of(simpleFolder1, simpleFolder2),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\directory"),
                metadata);
        SimpleFolder root = new SimpleFolder("",
                new java.util.LinkedList<>(),
                List.of(simpleFolder3),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\"),
                metadata);

        SimpleDisk simpleDisk = new SimpleDisk("A",
                root,
                root.getSize() * 3,
                new java.util.HashMap<>());

        SimpleSavStructure savFile = new SimpleSavStructure(
                "CupsOfCoffee",
                new SimpleDisk[] { simpleDisk },
                new java.util.HashMap<>());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",
                "CupsOfCoffee.sav");

        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(savStructureWriter.toBytes(savFile));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
