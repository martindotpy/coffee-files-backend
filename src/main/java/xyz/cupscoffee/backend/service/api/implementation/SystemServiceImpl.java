package xyz.cupscoffee.backend.service.api.implementation;

import org.springframework.stereotype.Service;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;
import xyz.cupscoffee.files.api.Metadata;
import xyz.cupscoffee.files.api.SavStructure;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import xyz.cupscoffee.backend.service.api.interfaces.SystemService;

@Service
public class SystemServiceImpl implements SystemService {
    @Override
    public SavStructure createDefaultSavStructure() {
        throw new UnsupportedOperationException("Unimplemented method 'createDefaultSavStructure'");
    }

    @Override
    public SavStructure syncSavStructure() {
        throw new UnsupportedOperationException("Unimplemented method 'syncSavStructure'");
    }

    @Override
    public byte[] export(SavStructure savFile) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();

        // Get the header
        sb.append(savFile.getHeader() + "\n");

        // Write the disks
        Disk[] disks = savFile.getDisks();
        for (int i = 0; i < disks.length; i++) {
            Disk disk = disks[i];
            sb.append(String.format("%s(%d)[%s]:", disk.getName(), disk.getLimitSize(), disk.getMetadata()));
            loadFolderAsString(disks[i].getRootFolder(), sb);

            sb.append("\n");
        }

        sb.append(savFile.getMetadata());

        String savFileContent = sb.toString();

        return savFileContent.getBytes();
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
