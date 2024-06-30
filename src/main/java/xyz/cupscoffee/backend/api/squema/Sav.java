package xyz.cupscoffee.backend.api.squema;

import lombok.Getter;

import java.util.Map;

@Getter
public class Sav {
    private Map<String, String> settings;
    private Disk[] disks;
}
