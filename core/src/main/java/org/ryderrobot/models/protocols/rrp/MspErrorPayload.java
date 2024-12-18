package org.ryderrobot.models.protocols.rrp;

public class MspErrorPayload {
    MspErrorPayload(final String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }

    private final String message;
}
