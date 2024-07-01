package xyz.cupscoffee.backend.service.api.implementation;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;
import xyz.cupscoffee.files.api.Metadata;
import xyz.cupscoffee.files.api.SavFileReader;
import xyz.cupscoffee.files.api.SavStructure;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;
import xyz.cupscoffee.files.api.implementation.SimpleDisk;
import xyz.cupscoffee.files.api.implementation.SimpleFile;
import xyz.cupscoffee.files.api.implementation.SimpleFolder;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import xyz.cupscoffee.backend.service.api.interfaces.SystemService;
import xyz.cupscoffee.backend.util.PathUtil;

@Service
@AllArgsConstructor
public class SystemServiceImpl implements SystemService {
    private final HttpSession session;

    @Override
    public void importSavStructure(InputStream inputStream) throws InvalidFormatFileException {
        SavStructure savStructure = SavFileReader.readSavFile(inputStream);
        session.setAttribute("file", savStructure);
    }

    @Override
    public SavStructure createDefaultSavStructure() throws FileNotFoundException, IOException {
        // Load golden_sunrise.json and golden_sunrise.jpg from resources
        java.io.File goldenSunriseJsonFile = PathUtil.getResourcePath()
                .resolve(Path.of("themes", "golden_sunrise.json"))
                .toFile();
        java.io.File goldenSunriseJpgFile = PathUtil.getResourcePath()
                .resolve(Path.of("themes", "golden_sunrise.jpg"))
                .toFile();

        HashMap<String, String> metadata = new HashMap<>();
        metadata.put("author", "user");

        HashMap<String, String> metadataJson = new HashMap<>();
        metadataJson.put("FileType", "JSON");
        metadataJson.put("author", "user");

        HashMap<String, String> metadataJpg = new HashMap<>();
        metadataJpg.put("FileType", "JPG");
        metadataJpg.put("author", "user");

        SimpleFile goldenSunriseJson = null;
        try (FileInputStream fis = new FileInputStream(goldenSunriseJsonFile)) {
            goldenSunriseJson = new SimpleFile(
                    goldenSunriseJsonFile.getName(),
                    ByteBuffer.wrap(fis.readAllBytes()),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Path.of("A:", "themes", goldenSunriseJsonFile.getName()),
                    metadataJson);

        } catch (FileNotFoundException e) {
            throw e;
        }
        SimpleFile goldenSunriseJpg = null;
        try (FileInputStream fis = new FileInputStream(goldenSunriseJpgFile)) {
            goldenSunriseJpg = new SimpleFile(
                    goldenSunriseJpgFile.getName(),
                    ByteBuffer.wrap(fis.readAllBytes()),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Path.of("B:", "user", "Images", "themes", goldenSunriseJpgFile.getName()),
                    metadataJpg);

        } catch (FileNotFoundException e) {
            throw e;
        }

        SimpleFolder themesA = new SimpleFolder(
                "themes",
                List.of(goldenSunriseJson),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:", "themes"),
                metadata);
        SimpleFolder themesB = new SimpleFolder(
                "themes",
                List.of(goldenSunriseJpg),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("B:", "user", "Images", "themes"),
                metadata);

        SimpleFolder documents = new SimpleFolder(
                "Documents",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("B:", "user", "Documents"),
                metadata);
        SimpleFolder downloads = new SimpleFolder(
                "Downloads",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("B:", "user", "Downloads"),
                metadata);
        SimpleFolder images = new SimpleFolder(
                "Images",
                new LinkedList<>(),
                List.of(themesB),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("B:", "user", "Images"),
                metadata);
        SimpleFolder projects = new SimpleFolder(
                "Projects",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("B:", "user", "Projects"),
                metadata);

        SimpleFolder user = new SimpleFolder(
                "user",
                new LinkedList<>(),
                List.of(documents, downloads, images, projects),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("B:", "user"),
                metadata);

        SimpleFolder rootA = new SimpleFolder(
                "",
                new LinkedList<>(),
                List.of(themesA),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\"),
                metadata);
        SimpleFolder rootB = new SimpleFolder(
                "",
                new LinkedList<>(),
                List.of(user),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("B:\\"),
                metadata);

        SimpleDisk diskA = new SimpleDisk(
                "A",
                rootA,
                2400,
                metadata);
        SimpleDisk diskB = new SimpleDisk(
                "B",
                rootB,
                10000000,
                metadata);

        Disk[] disks = { diskA, diskB };

        SimpleSavStructure savStructure = new SimpleSavStructure(
                "CupsOfCoffee",
                disks,
                metadata);

        session.setAttribute("file", savStructure);

        return savStructure;
    }

    @Override
    public SavStructure syncSavStructure() {
        return (SavStructure) session.getAttribute("file");
    }

    @Override
    public byte[] export() throws UnsupportedEncodingException {
        SavStructure savStructure = (SavStructure) session.getAttribute("file");
        StringBuilder sb = new StringBuilder();

        // Get the header
        sb.append(savStructure.getHeader() + "\n");

        // Write the disks
        Disk[] disks = savStructure.getDisks();
        for (int i = 0; i < disks.length; i++) {
            Disk disk = disks[i];
            sb.append(String.format("%s(%d)[%s]:", disk.getName(), disk.getLimitSize(), disk.getMetadata()));
            loadFolderAsString(disks[i].getRootFolder(), sb);

            sb.append("\n");
        }

        sb.append(savStructure.getMetadata());

        String savStructureContent = sb.toString();

        return savStructureContent.getBytes();
    }

    private void loadFolderAsString(Folder folder, StringBuilder sb) {
        sb.append("|" + folder.getName() + "<");

        // Load folders
        List<Folder> subFolders = folder.getFolders();
        for (Folder subFolder : subFolders) {
            loadFolderAsString(subFolder, sb);
        }

        // Load files
        List<File> files = folder.getFiles();
        for (File file : files) {
            loadFileAsString(file, sb);
        }

        sb.append(">");

        // Load metadata
        loadMetadataAsString(folder, sb);
    }

    private void loadFileAsString(File file, StringBuilder sb) {
        sb.append("*" + file.getName() + "{");

        String content = new String(file.getContent().array(), StandardCharsets.UTF_8);

        sb.append(content);

        sb.append("}");

        // Load metadata
        loadMetadataAsString(file, sb);
    }

    private void loadMetadataAsString(Metadata metadata, StringBuilder sb) {
        sb.append("[");

        Arrays.asList(Metadata.class.getMethods()).forEach(method -> {
            if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
                try {
                    sb.append(method.getName().substring(3) + ":");

                    if (method.getReturnType().equals(LocalDateTime.class)) {
                        LocalDateTime localDateTime = (LocalDateTime) method.invoke(metadata);
                        sb.append(localDateTime.toEpochSecond(ZoneOffset.of("Z")) + ",");
                        return;
                    }

                    sb.append(method.invoke(metadata) + ",");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sb.deleteCharAt(sb.length() - 1);

        sb.append("]");
    }
}
