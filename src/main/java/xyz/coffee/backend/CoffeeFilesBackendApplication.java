package xyz.coffee.backend;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Temp
import org.springframework.context.annotation.Bean;

import xyz.coffee.backend.service.auth.api.interfaces.SavStructureExporter;
import xyz.cupscoffee.files.api.implementation.SimpleDisk;
import xyz.cupscoffee.files.api.implementation.SimpleFile;
import xyz.cupscoffee.files.api.implementation.SimpleFolder;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;

@SpringBootApplication
public class CoffeeFilesBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoffeeFilesBackendApplication.class, args);
    }

    // Temp
    @Autowired
    private SavStructureExporter savStructureWriter;

    @Bean
    CommandLineRunner init() {
        return args -> {
            String fileContent = "console.log('Hello, World!');\n";
            Base64.Encoder encoder = Base64.getEncoder();
            String encodedFileContent = encoder.encodeToString(fileContent.getBytes());
            ByteBuffer fileContentBuffer = ByteBuffer.wrap(encodedFileContent.getBytes());
            Map<String, String> metadata = new java.util.HashMap<>();
            metadata.put("author", "Elder");

            SimpleFile simpleFile = new SimpleFile("archivo.js",
                    fileContentBuffer,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Path.of("A:\\directorio\\otro_directorio\\archivo.js"),
                    metadata);
            SimpleFolder simpleFolder1 = new SimpleFolder("otro_directorio",
                    List.of(simpleFile),
                    new LinkedList<>(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Path.of("A:\\directorio\\otro_directorio"),
                    metadata);
            SimpleFolder simpleFolder2 = new SimpleFolder("directorio",
                    new java.util.LinkedList<>(),
                    new LinkedList<>(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Path.of("A:\\directorio\\directorio"),
                    metadata);
            SimpleFolder simpleFolder3 = new SimpleFolder("directorio",
                    new java.util.LinkedList<>(),
                    List.of(simpleFolder1, simpleFolder2),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Path.of("A:\\directorio"),
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

            try (FileOutputStream fileOutputStream = new FileOutputStream("tcoc.sav")) {
                byte[] savFileBytes = savStructureWriter.toBytes(savFile);
                fileOutputStream.write(savFileBytes);
            } catch (Exception e) {

            }
        };
    }
}
