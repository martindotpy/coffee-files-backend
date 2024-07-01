package xyz.cupscoffee.backend.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.cupscoffee.backend.api.squema.enums.FileType;
import xyz.cupscoffee.backend.api.squema.PathSquema;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFileRequest {
    private PathSquema path;
    private FileType type;
}
