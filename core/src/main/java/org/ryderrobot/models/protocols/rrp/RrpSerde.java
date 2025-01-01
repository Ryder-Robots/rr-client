package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.JsonValue;

public interface RrpSerde<P> {

    /**
     * serializes event payload for transport.
     *
     * @param payload
     * @return
     */
    JsonValue serialize(P payload);
}
