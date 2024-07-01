package xyz.cupscoffee.backend.api.squema;

import java.time.ZoneOffset;

import lombok.Getter;
import xyz.cupscoffee.backend.api.squema.enums.FileType;
import xyz.cupscoffee.files.api.File;

@Getter
public class FileStructureSquema extends PathSquema {
    private String name;
    private Long createdAt;
    private Long lastModifiedAt;
    private Long size;
    private FileType type;

    public FileStructureSquema(String path, String name, Long createdAt, Long lastModifiedAt, Long size,
            FileType type) {
        super(path);
        this.name = name;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.size = size;
        this.type = type;
    }

    public static FileStructureSquema from(File file) {
        FileType type = null;
        try {
            FileType.valueOf(file.getOtherMetadata().get("FileType"));
        } catch (NullPointerException e) {
            type = FileType.TXT;
        }

        return new FileStructureSquema(
                file.getPath().toString(),
                file.getName(),
                file.getCreatedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                file.getLastModifiedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                file.getSize(),
                type
        );
    }
}
