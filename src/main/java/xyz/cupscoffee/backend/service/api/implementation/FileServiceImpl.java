package xyz.cupscoffee.backend.service.api.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;
import xyz.cupscoffee.files.api.SavStructure;
import xyz.cupscoffee.files.api.implementation.SimpleFile;
import xyz.cupscoffee.backend.api.request.CreateFileRequest;
import xyz.cupscoffee.backend.api.request.DeleteFileRequest;
import xyz.cupscoffee.backend.api.request.DownloadFileRequest;
import xyz.cupscoffee.backend.api.request.EditFileRequest;
import xyz.cupscoffee.backend.api.request.MoveFileRequest;
import xyz.cupscoffee.backend.api.request.ReadContentFileRequest;
import xyz.cupscoffee.backend.api.squema.enums.FileType;
import xyz.cupscoffee.backend.exception.InvalidPathException;
import xyz.cupscoffee.backend.service.api.interfaces.FileService;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final HttpSession session;

    @Override
    public File uploadFile(String rawPath, FileType type, InputStream inputStream) throws IOException {
        int indexOfColon = rawPath.indexOf(":");
        String diskName = rawPath.substring(0, indexOfColon);

        SavStructure savStructure = (SavStructure) session.getAttribute("file");
        Disk[] disks = savStructure.getDisks();

        for (int i = 0; i < disks.length; i++) {
            if (!disks[i].getName().equals(diskName)) {
                continue;
            }

            String[] pathString = rawPath.substring(indexOfColon + 2).split("\\\\"); // Skip the "" - Root with the + 2
            Path path = Path.of("", Arrays.copyOfRange(pathString, 0, pathString.length - 1));
            Folder folder = findFolderByPath(disks[i].getRootFolder(), path);

            if (folder == null) {
                throw new InvalidPathException("Invalid path: " + rawPath);
            }

            HashMap<String, String> otherMetadata = new HashMap<>();
            otherMetadata.put("FileType", type.toString());
            otherMetadata.put("author", "user");

            File newFile = new SimpleFile(
                    pathString[pathString.length - 1],
                    ByteBuffer.wrap(inputStream.readAllBytes()),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    path.resolve(pathString[pathString.length - 1]),
                    otherMetadata);

            folder.getFiles().add(newFile);

            return newFile;
        }

        throw new InvalidPathException("Invalid path: " + rawPath);
    }

    private Folder findFolderByPath(Folder parentFolder, Path path) {
        if (path.toString().startsWith(parentFolder.getPath().toString())) {
            for (Folder folder : parentFolder.getFolders()) {
                if (folder.getPath().equals(path)) {
                    return folder;
                }

                Folder foundFolder = findFolderByPath(folder, path);

                if (foundFolder != null) {
                    return foundFolder;
                }
            }
        }

        return null;
    }

    @Override
    public File createFile(CreateFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'createFile'");
    }

    @Override
    public String readFileContent(ReadContentFileRequest request) {
        SavStructure savStructure = (SavStructure) session.getAttribute("file");
        String rawPath = request.getPath().getPath();

        // Find disk
        int indexOfColon = rawPath.indexOf(":");
        String diskName = rawPath.substring(0, indexOfColon);

        Disk[] disks = savStructure.getDisks();
        for (int i = 0; i < disks.length; i++) {
            if (!disks[i].getName().equals(diskName)) {
                continue;
            }

            Path path = Path.of("", rawPath.substring(indexOfColon + 2).split("\\\\")); // Skip the "" - Root with the
                                                                                        // + 2
            File file = findFileByPath(disks[i].getRootFolder(), path);

            if (file == null) {
                throw new InvalidPathException("Invalid path: " + rawPath);
            }

            return new String(Base64.getEncoder().encode(file.getContent().duplicate()).array());
        }

        throw new InvalidPathException("Invalid path: " + rawPath);
    }

    private File findFileByPath(Folder parentFolder, Path path) {
        if (path.toString().startsWith(parentFolder.getPath().toString())) {
            for (File file : parentFolder.getFiles()) {
                if (file.getName().equals(path.getFileName().toString())) {
                    return file;
                }
            }

            for (Folder folder : parentFolder.getFolders()) {
                File file = findFileByPath(folder, path);

                if (file != null) {
                    return file;
                }
            }
        }

        return null;
    }

    @Override
    public File editFile(EditFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'editFile'");
    }

    @Override
    public void deleteFile(DeleteFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteFile'");
    }

    @Override
    public byte[] downloadFile(DownloadFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'downloadFile'");
    }

    @Override
    public File moveFile(MoveFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'moveFile'");
    }
}
