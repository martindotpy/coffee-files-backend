package xyz.cupscoffee.backend.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import xyz.cupscoffee.backend.api.request.CreateFileRequest;
import xyz.cupscoffee.backend.api.request.DeleteFileRequest;
import xyz.cupscoffee.backend.api.request.DownloadFileRequest;
import xyz.cupscoffee.backend.api.request.EditFileRequest;
import xyz.cupscoffee.backend.api.request.MoveFileRequest;
import xyz.cupscoffee.backend.api.request.ReadFileRequest;
import xyz.cupscoffee.backend.api.request.UploadFileRequest;
import xyz.cupscoffee.backend.api.response.ReadFileContentResponse;
import xyz.cupscoffee.backend.api.squema.FileSquema;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {
    private final HttpSession session;

    @PatchMapping("/files/upload")
    public ResponseEntity<FileSquema> uploadFile(
            @RequestPart("file") MultipartFile file,
            UploadFileRequest uploadFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping("/files/create")
    public ResponseEntity<FileSquema> createFile(CreateFileRequest createFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/files/read")
    public ResponseEntity<ReadFileContentResponse> readFile(ReadFileRequest readFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PatchMapping("/files/edit")
    public ResponseEntity<FileSquema> editFile(EditFileRequest editFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @DeleteMapping("/files/delete")
    public ResponseEntity<Void> deleteFile(DeleteFileRequest deleteFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/files/delete")
    public ResponseEntity<byte[]> downloadFile(DownloadFileRequest downloadFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping("/files/move")
    public ResponseEntity<FileSquema> moveFile(MoveFileRequest moveFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
