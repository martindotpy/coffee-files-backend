package xyz.cupscoffee.backend.service.api.implementation;

import java.nio.file.Path;
import java.util.Base64;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;
import xyz.cupscoffee.files.api.SavStructure;

import xyz.cupscoffee.backend.api.request.CreateFileRequest;
import xyz.cupscoffee.backend.api.request.DeleteFileRequest;
import xyz.cupscoffee.backend.api.request.DownloadFileRequest;
import xyz.cupscoffee.backend.api.request.EditFileRequest;
import xyz.cupscoffee.backend.api.request.MoveFileRequest;
import xyz.cupscoffee.backend.api.request.ReadContentFileRequest;
import xyz.cupscoffee.backend.api.request.UploadFileRequest;
import xyz.cupscoffee.backend.exception.InvalidPathException;
import xyz.cupscoffee.backend.service.api.interfaces.FileService;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final HttpSession session;

    @Override
    public File uploadFile(UploadFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'uploadFile'");
    }

    @Override
    public File createFile(CreateFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'createFile'");
    }

    @Override
    public String readFileContent(ReadContentFileRequest request) throws InvalidPathException {
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
