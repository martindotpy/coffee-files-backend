package xyz.cupscoffee.backend.api.squema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.cupscoffee.files.api.SavStructure;

import java.util.Arrays;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SavSquema {
    private Map<String, String> settings;
    private DiskSquema[] disks;

    public static SavSquema from(SavStructure savStructure) {
        DiskSquema[] disks = Arrays.asList(savStructure.getDisks()).stream()
                .map(DiskSquema::from)
                .toArray(DiskSquema[]::new);

        return new SavSquema(savStructure.getMetadata(), disks);
    }
}
