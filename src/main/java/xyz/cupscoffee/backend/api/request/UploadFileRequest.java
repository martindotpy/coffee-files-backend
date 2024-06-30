package xyz.cupscoffee.backend.api.request;

import lombok.Getter;

import xyz.cupscoffee.backend.api.squema.enums.FileType;
import xyz.cupscoffee.backend.api.squema.PathSquema;

@Getter
public class UploadFileRequest {
    private PathSquema path;
    private FileType type;
}
