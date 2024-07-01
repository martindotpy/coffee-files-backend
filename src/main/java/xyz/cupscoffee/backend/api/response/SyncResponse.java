package xyz.cupscoffee.backend.api.response;

import lombok.Builder;
import lombok.Getter;
import xyz.cupscoffee.backend.api.squema.SavSquema;

@Getter
@Builder
public class SyncResponse {
    private SavSquema system;
}
