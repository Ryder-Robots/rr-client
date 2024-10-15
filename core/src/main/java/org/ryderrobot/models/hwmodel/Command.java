package org.ryderrobot.models.hwmodel;

/**
 * base modelling template. This is the standard for sending commands to the drone.
 * @param <T> the payload.
 */

public interface Command<T> {
    Action getAction();

    T getPayload();
}
