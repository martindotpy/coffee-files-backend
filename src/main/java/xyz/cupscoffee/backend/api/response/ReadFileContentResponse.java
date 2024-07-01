package xyz.cupscoffee.backend.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadFileContentResponse {
    private String content;
}
