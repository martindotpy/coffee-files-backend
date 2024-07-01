package xyz.cupscoffee.backend.api.response;

import lombok.Builder;
import lombok.Getter;
import xyz.cupscoffee.backend.api.squema.enums.SystemStatus;

@Getter
@Builder
public class SystemStatusResponse {
    private SystemStatus status;
}
