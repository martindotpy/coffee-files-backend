package xyz.cupscoffee.backend.api.squema;

import lombok.Getter;

@Getter
public class DiskSquema {
    private String name;
    private FolderSquema root;
    private Long limitSize;
    private Long occupiedSize;
}
