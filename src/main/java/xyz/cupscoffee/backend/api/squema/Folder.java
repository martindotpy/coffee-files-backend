package xyz.cupscoffee.backend.api.squema;

import lombok.Getter;

@Getter
public class Folder {
    private String name;
    private File[] files;
    private Folder[] folders;
    private Long createdAt;
    private Long lastModifiedAt;
    private Long size;
    private boolean isRoot;
}
