package xyz.cupscoffee.backend.api.squema;

import lombok.Getter;

import java.time.ZoneOffset;

import xyz.cupscoffee.files.api.File;

import xyz.cupscoffee.backend.api.squema.enums.FileType;

@Getter
public class FileStructureSquema extends PathSquema {
    private String name;
    private Long createdAt;
    private Long lastModifiedAt;
    private Long size;
    private FileType type;

    public FileStructureSquema(String name, Long createdAt, Long lastModifiedAt, Long size,
            FileType type, String path) {
        super(path);
        this.name = name;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.size = size;
        this.type = type;
    }

    public static FileStructureSquema from(File file, String diskName) {
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

        return new FileStructureSquema(
                file.getName(),
                file.getCreatedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                file.getLastModifiedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                file.getSize(),
                type,
                absolutePath);
    }
}
