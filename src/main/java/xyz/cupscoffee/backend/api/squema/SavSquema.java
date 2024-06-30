package xyz.cupscoffee.backend.api.squema;

import lombok.Getter;

import java.util.Map;

@Getter
public class SavSquema {
    private Map<String, String> settings;
    private DiskSquema[] disks;
}
