package xyz.cupscoffee.backend.api.squema;

import java.time.ZoneOffset;

import lombok.Getter;
import xyz.cupscoffee.files.api.Folder;

@Getter
public class FolderSquema extends PathSquema {
    private String name;
    private FileStructureSquema[] files;
    private FolderSquema[] folders;
    private Long createdAt;
    private Long lastModifiedAt;
    private Long size;
    private boolean isRoot;

    public FolderSquema(String name, FileStructureSquema[] files, FolderSquema[] folders, Long createdAt,
            Long lastModifiedAt, Long size, boolean isRoot, String path) {
        super(path);
        this.name = name;
        this.files = files;
        this.folders = folders;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.size = size;
        this.isRoot = isRoot;
    }

    public static FolderSquema from(Folder folder) {
        if (folder == null)
            return null;

        FileStructureSquema[] files = folder.getFiles().stream()
                .map(FileStructureSquema::from)
                .toArray(FileStructureSquema[]::new);
        FolderSquema[] folders = folder.getFolders().stream()
                .map(FolderSquema::from)
                .toArray(FolderSquema[]::new);

        boolean isRoot = folder.getPath().toString().split("\\\\").length == 1;

        return new FolderSquema(
                folder.getName(),
                files,
                folders,
                folder.getCreatedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                folder.getLastModifiedDateTime().toEpochSecond(ZoneOffset.of("Z")),
                folder.getSize(),
                isRoot,
                folder.getPath().toString());
    }
}
