package xyz.cupscoffee.backend.service.api.interfaces;

import java.io.IOException;
import java.io.InputStream;

import xyz.cupscoffee.backend.api.request.CreateFileRequest;
import xyz.cupscoffee.backend.api.request.DeleteFileRequest;
import xyz.cupscoffee.backend.api.request.DownloadFileRequest;
import xyz.cupscoffee.backend.api.request.EditFileRequest;
import xyz.cupscoffee.backend.api.request.MoveFileRequest;
import xyz.cupscoffee.backend.api.request.ReadContentFileRequest;
import xyz.cupscoffee.backend.api.squema.enums.FileType;
import xyz.cupscoffee.files.api.File;

public interface FileService {
    File uploadFile(String path, FileType type, InputStream inputStream) throws IOException;

    File createFile(CreateFileRequest request);

    String readFileContent(ReadContentFileRequest request);

    File editFile(EditFileRequest request);

    void deleteFile(DeleteFileRequest request);

    byte[] downloadFile(DownloadFileRequest request);

    File moveFile(MoveFileRequest request);
}
