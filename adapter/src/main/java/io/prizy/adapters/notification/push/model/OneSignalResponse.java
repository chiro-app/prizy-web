package io.prizy.adapters.notification.push.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Nidhal Dogga
 * @created 26/07/2022 10:55
 */


public record OneSignalResponse(
    UUID id,
    Long recipients,
    @JsonProperty("external_id")
    UUID externalId,
    Object errors
) {

    public boolean isSuccessful() {
        return errors == null && id != null;
    }

}
