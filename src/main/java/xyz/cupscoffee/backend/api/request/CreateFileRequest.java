package xyz.cupscoffee.backend.api.request;

import lombok.Getter;

import xyz.cupscoffee.backend.api.squema.enums.FileType;
import xyz.cupscoffee.backend.api.squema.PathSquema;

@Getter
public class CreateFileRequest {
    private PathSquema path;
    private String name;
    private String content;
    private FileType type;
}
