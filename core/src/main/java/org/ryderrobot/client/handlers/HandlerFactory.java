package org.ryderrobot.client.handlers;

import org.ryderrobot.env.Queue;
import org.ryderrobot.models.DroneManifest;

/**
 * When given a drone manifest returns the correct handler
 */

public class HandlerFactory {
    public static ControllerHandler getHandler(DroneManifest manifest, Queue<String> ingress, Queue<String> egress) {
        ControllerHandler handler = new SkuLd001();

        handler.setEgress(egress);
        handler.setIngress(ingress);
        return handler;
    }
}
