package xyz.cupscoffee.backend.api.response;

import lombok.Getter;
import xyz.cupscoffee.backend.api.squema.enums.SystemStatus;

@Getter
public class SystemStatusResponse {
    private SystemStatus status;
}
