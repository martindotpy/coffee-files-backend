package xyz.cupscoffee.backend.api.squema;

import lombok.Getter;
import xyz.cupscoffee.backend.api.squema.enums.FileType;

@Getter
public class FileSquema extends PathSquema {
    private String name;
    private String content;
    private Long createdAt;
    private Long lastModifiedAt;
    private Long size;
    private FileType type;
}
