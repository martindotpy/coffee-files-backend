package xyz.cupscoffee.backend.api.squema;

import java.time.ZoneOffset;

import lombok.Getter;
import xyz.cupscoffee.backend.api.squema.enums.FileType;
import xyz.cupscoffee.files.api.File;

@Getter
public class FileSquema extends PathSquema {
    private String name;
    private String content;
    private Long createdAt;
    private Long lastModifiedAt;
    private Long size;
    private FileType type;

    public FileSquema(String name, String content, Long createdAt, Long lastModifiedAt, Long size, String path,
            FileType type) {
        super(path);
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.size = size;
        this.type = type;
    }

    public static FileSquema from(File file) {
        FileType type = null;
        try {
            FileType.valueOf(file.getOtherMetadata().get("FileType"));
        } catch (NullPointerException e) {
            type = FileType.TXT;
        }

        return new FileSquema(
                file.getName(),
                file.getContent().toString(),
                file.getCreatedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                file.getLastModifiedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                file.getSize(),
                file.getPath().toString(),
                type);
    }
}
