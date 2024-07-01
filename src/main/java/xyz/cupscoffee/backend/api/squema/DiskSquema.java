package xyz.cupscoffee.backend.api.squema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.cupscoffee.files.api.Disk;

@Getter
@AllArgsConstructor
public class DiskSquema {
    private String name;
    private FolderSquema root;
    private Long limitSize;
    private Long occupiedSize;

    public static DiskSquema from(Disk disk) {
        return new DiskSquema(
                disk.getName(),
                FolderSquema.from(disk.getRootFolder(), disk.getName()),
                disk.getLimitSize(),
                disk.getOccupiedSize());
    }
}
