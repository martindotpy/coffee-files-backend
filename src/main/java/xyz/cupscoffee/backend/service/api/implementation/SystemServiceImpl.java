package xyz.cupscoffee.backend.service.api.implementation;

import org.apache.tomcat.util.http.fileupload.FileUtils;
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
import java.util.*;

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
                    Path.of("", "themes", goldenSunriseJsonFile.getName()),
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
                    Path.of("", "user", "Images", "themes", goldenSunriseJpgFile.getName()),
                    metadataJpg);

        } catch (FileNotFoundException e) {
            throw e;
        }

        List<File> filesThemesA = new LinkedList<>();
        filesThemesA.add(goldenSunriseJson);
        SimpleFolder themesA = new SimpleFolder(
                "themes",
                filesThemesA,
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "themes"),
                metadata);
        List<File> filesThemesB = new LinkedList<>();
        filesThemesB.add(goldenSunriseJpg);
        SimpleFolder themesB = new SimpleFolder(
                "themes",
                filesThemesB,
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "user", "Images", "themes"),
                metadata);

        SimpleFolder documents = new SimpleFolder(
                "Documents",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "user", "Documents"),
                metadata);
        SimpleFolder downloads = new SimpleFolder(
                "Downloads",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "user", "Downloads"),
                metadata);
        List<Folder> foldersImages = new LinkedList<>();
        foldersImages.add(themesB);
        SimpleFolder images = new SimpleFolder(
                "Images",
                new LinkedList<>(),
                foldersImages,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "user", "Images"),
                metadata);
        SimpleFolder projects = new SimpleFolder(
                "Projects",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "user", "Projects"),
                metadata);

        List<Folder> foldersUser = new LinkedList<>();
        foldersUser.add(documents);
        foldersUser.add(downloads);
        foldersUser.add(images);
        foldersUser.add(projects);
        SimpleFolder user = new SimpleFolder(
                "user",
                new LinkedList<>(),
                foldersUser,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "user"),
                metadata);

        List<Folder> foldersA = new LinkedList<>();
        foldersA.add(themesA);
        SimpleFolder rootA = new SimpleFolder(
                "",
                new LinkedList<>(),
                foldersA,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of(""),
                metadata);
        List<Folder> foldersB = new LinkedList<>();
        foldersB.add(user);
        SimpleFolder rootB = new SimpleFolder(
                "",
                new LinkedList<>(),
                foldersB,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of(""),
                metadata);

        SimpleDisk diskA = new SimpleDisk(
                "A",
                rootA,
                10000000,
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
        sb.append(String.format("%-16s",savStructure.getHeader()) + "\n");

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

        String content = new String(Base64.getEncoder().encode(file.getContent().duplicate()).array());

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
                        sb.append(localDateTime.toEpochSecond(ZoneOffset.of("Z")) + ";");
                        return;
                    }

                    sb.append(method.invoke(metadata) + ";");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sb.deleteCharAt(sb.length() - 1);

        sb.append("]");
    }
}
