package xyz.cupscoffee.backend.util;

import java.nio.file.Path;

public class PathUtil {
    public static Path getResourcePath() {
        return Path.of("src", "main", "resources");
    }
}
