package xyz.cupscoffee.backend.api.squema;

import javax.sound.sampled.AudioFileFormat.Type;

import lombok.Getter;

@Getter
public class FileStructureSquema extends PathSquema {
    private String name;
    private Long createdAt;
    private Long lastModifiedAt;
    private Long size;
    private Type type;
}
