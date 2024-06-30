package xyz.cupscoffee.backend.service.api.implementation;

import org.springframework.stereotype.Service;

import xyz.cupscoffee.files.api.File;

import xyz.cupscoffee.backend.api.request.CreateFileRequest;
import xyz.cupscoffee.backend.api.request.DeleteFileRequest;
import xyz.cupscoffee.backend.api.request.DownloadFileRequest;
import xyz.cupscoffee.backend.api.request.EditFileRequest;
import xyz.cupscoffee.backend.api.request.MoveFileRequest;
import xyz.cupscoffee.backend.api.request.ReadContentFileRequest;
import xyz.cupscoffee.backend.api.request.UploadFileRequest;
import xyz.cupscoffee.backend.service.api.interfaces.FileService;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public File uploadFile(UploadFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'uploadFile'");
    }

    @Override
    public File createFile(CreateFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'createFile'");
    }

    @Override
    public String readFileContent(ReadContentFileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'readFileContent'");
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
