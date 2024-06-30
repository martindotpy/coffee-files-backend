package xyz.cupscoffee.backend.api.request;

import lombok.Getter;

import xyz.cupscoffee.backend.api.squema.PathSquema;

@Getter
public class DownloadFileRequest {
    private PathSquema path;
}
