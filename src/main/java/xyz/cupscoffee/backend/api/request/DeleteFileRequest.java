package xyz.cupscoffee.backend.api.request;

import lombok.Getter;
import xyz.cupscoffee.backend.api.squema.Path;

@Getter
public class DeleteFileRequest {
    private Path path;
}
