package xyz.cupscoffee.backend.api.request;

import lombok.Getter;

import xyz.cupscoffee.backend.api.squema.enums.FileType;
import xyz.cupscoffee.backend.api.squema.Path;

@Getter
public class EditFileRequest {
    private Path path;
    private String content;
    private FileType type;
}
