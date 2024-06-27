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

import xyz.coffee.backend.service.auth.api.interfaces.SavFileExporter;
import xyz.cupscoffee.files.api.implementation.BasicDisk;
import xyz.cupscoffee.files.api.implementation.BasicFile;
import xyz.cupscoffee.files.api.implementation.BasicFolder;
import xyz.cupscoffee.files.api.implementation.BasicSavFile;

@SpringBootApplication
public class CoffeeFilesBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoffeeFilesBackendApplication.class, args);
    }

    // Temp
    @Autowired
    private SavFileExporter savFileWriter;

    @Bean
    CommandLineRunner init() {
        return args -> {
            String fileContent = "console.log('Hello, World!');\n";
            Base64.Encoder encoder = Base64.getEncoder();
            String encodedFileContent = encoder.encodeToString(fileContent.getBytes());
            ByteBuffer fileContentBuffer = ByteBuffer.wrap(encodedFileContent.getBytes());
            Map<String, String> metadata = new java.util.HashMap<>();
            metadata.put("author", "Elder");

            BasicFile basicFile = new BasicFile("archivo.js",
                    fileContentBuffer,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    fileContentBuffer.capacity(),
                    Path.of("A:\\directorio\\otro_directorio\\archivo.js"),
                    metadata);
            BasicFolder basicFolder1 = new BasicFolder("otro_directorio",
                    List.of(basicFile),
                    new LinkedList<>(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    fileContentBuffer.capacity(),
                    Path.of("A:\\directorio\\otro_directorio"),
                    metadata);
            BasicFolder basicFolder2 = new BasicFolder("directorio",
                    new java.util.LinkedList<>(),
                    new LinkedList<>(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    fileContentBuffer.capacity(),
                    Path.of("A:\\directorio\\directorio"),
                    metadata);
            BasicFolder basicFolder3 = new BasicFolder("directorio",
                    new java.util.LinkedList<>(),
                    List.of(basicFolder1, basicFolder2),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    fileContentBuffer.capacity(),
                    Path.of("A:\\directorio"),
                    metadata);
            BasicFolder root = new BasicFolder("",
                    new java.util.LinkedList<>(),
                    List.of(basicFolder3),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    fileContentBuffer.capacity(),
                    Path.of("A:\\"),
                    metadata);

            BasicDisk basicDisk = new BasicDisk("A",
                    root,
                    root.getSize() * 3,
                    root.getSize(),
                    new java.util.HashMap<>());

            BasicSavFile savFile = new BasicSavFile(
                    "CupsOfCoffee",
                    new BasicDisk[] { basicDisk },
                    new java.util.HashMap<>());

            try (FileOutputStream fileOutputStream = new FileOutputStream("tcoc.sav")) {
                byte[] savFileBytes = savFileWriter.export(savFile);
                fileOutputStream.write(savFileBytes);
            } catch (Exception e) {
                
            }
        };
    }
}
