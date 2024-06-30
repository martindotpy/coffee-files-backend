package xyz.cupscoffee.backend.service.api.interfaces;

import xyz.cupscoffee.backend.api.request.CreateFileRequest;
import xyz.cupscoffee.backend.api.request.DeleteFileRequest;
import xyz.cupscoffee.backend.api.request.DownloadFileRequest;
import xyz.cupscoffee.backend.api.request.EditFileRequest;
import xyz.cupscoffee.backend.api.request.MoveFileRequest;
import xyz.cupscoffee.backend.api.request.ReadFileRequest;
import xyz.cupscoffee.backend.api.request.UploadFileRequest;
import xyz.cupscoffee.files.api.File;

public interface FileService {
    File uploadFile(UploadFileRequest request);

    File createFile(CreateFileRequest request);

    String readFileContent(ReadFileRequest request);

    File editFile(EditFileRequest request);

    void deleteFile(DeleteFileRequest request);

    byte[] downloadFile(DownloadFileRequest request);

    File moveFile(MoveFileRequest request);
}
