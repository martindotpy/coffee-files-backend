package xyz.cupscoffee.backend.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Map;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;

@Component
@SessionScope
public class CupsOfCoffeeSavStructure extends SimpleSavStructure {
    public CupsOfCoffeeSavStructure(String header, Disk[] disks, Map<String, String> metadata) {
        super(header, disks, metadata);
    }
}
