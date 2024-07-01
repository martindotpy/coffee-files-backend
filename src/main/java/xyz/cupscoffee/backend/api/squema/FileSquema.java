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

    public static FileSquema from(File file, String diskName) {
        String fileTypeString = file.getOtherMetadata().get("FileType");
        FileType type = null;

        if (fileTypeString == null) {
            type = FileType.TXT;
        } else {
            type = FileType.valueOf(fileTypeString);
        }

        StringBuilder sb = new StringBuilder();
        file.getPath().forEach(p -> sb.append("\\" + p.toString()));

        String absolutePath = diskName + ":" + sb.toString();

        return new FileSquema(
                file.getName(),
                file.getContent().toString(),
                file.getCreatedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                file.getLastModifiedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                file.getSize(),
                absolutePath,
                type);
    }
}
