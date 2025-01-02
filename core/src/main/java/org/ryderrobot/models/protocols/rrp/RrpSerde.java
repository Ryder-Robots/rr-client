package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;

public interface RrpSerde<P> {

    /**
     * serializes event payload for transport.
     *
     * @param payload json object
     * @return event payload
     */
    JsonValue serialize(P payload);

    /**
     * deserialize payload to event payload
     *
     * @param payload
     * @return event payload
     */
    P deserialize(JsonValue payload);
}
