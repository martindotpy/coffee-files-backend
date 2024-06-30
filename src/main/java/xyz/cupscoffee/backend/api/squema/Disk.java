package xyz.cupscoffee.backend.api.squema;

import lombok.Getter;

@Getter
public class Disk {
    private String name;
    private Folder root;
    private Long limitSize;
    private Long occupiedSize;
}
