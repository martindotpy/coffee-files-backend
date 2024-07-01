package xyz.cupscoffee.backend.api;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import xyz.cupscoffee.backend.api.request.CreateFileRequest;
import xyz.cupscoffee.backend.api.request.DeleteFileRequest;
import xyz.cupscoffee.backend.api.request.DownloadFileRequest;
import xyz.cupscoffee.backend.api.request.EditFileRequest;
import xyz.cupscoffee.backend.api.request.MoveFileRequest;
import xyz.cupscoffee.backend.api.request.ReadContentFileRequest;
import xyz.cupscoffee.backend.api.request.UploadFileRequest;
import xyz.cupscoffee.backend.api.response.ReadFileContentResponse;
import xyz.cupscoffee.backend.api.squema.FileSquema;
import xyz.cupscoffee.backend.exception.InvalidPathException;
import xyz.cupscoffee.backend.service.api.interfaces.FileService;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @PatchMapping("/upload")
    public ResponseEntity<FileSquema> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestBody UploadFileRequest uploadFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping("/create")
    public ResponseEntity<FileSquema> createFile(@RequestBody CreateFileRequest createFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/read")
    public ResponseEntity<ReadFileContentResponse> readFile(@RequestBody ReadContentFileRequest readFileRequest)
            throws InvalidPathException {
        String content = fileService.readFileContent(readFileRequest);

        return ResponseEntity.ok(
                ReadFileContentResponse.builder()
                        .content(content)
                        .build());
    }

    @PatchMapping("/edit")
    public ResponseEntity<FileSquema> editFile(@RequestBody EditFileRequest editFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(@RequestBody DeleteFileRequest deleteFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestBody DownloadFileRequest downloadFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping("/move")
    public ResponseEntity<FileSquema> moveFile(@RequestBody MoveFileRequest moveFileRequest) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
