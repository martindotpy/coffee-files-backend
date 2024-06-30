package xyz.cupscoffee.backend.api.request;

import lombok.Getter;

import xyz.cupscoffee.backend.api.squema.Path;

@Getter
public class DownloadFileRequest {
    private Path path;
}
