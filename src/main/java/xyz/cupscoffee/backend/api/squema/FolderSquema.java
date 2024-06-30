package xyz.cupscoffee.backend.api.squema;

import lombok.Getter;

@Getter
public class FolderSquema {
    private String name;
    private FileSquema[] files;
    private FolderSquema[] folders;
    private Long createdAt;
    private Long lastModifiedAt;
    private Long size;
    private boolean isRoot;
}
