package org.ryderrobot.client.handlers;

import org.ryderrobot.env.Queue;
import org.ryderrobot.models.DroneManifest;
import org.ryderrobot.models.hwmodel.Action;
import org.ryderrobot.models.hwmodel.Observer;

/**
 * When given a drone manifest returns the correct handler
 */

public class HandlerFactory {
    public static ControllerHandler getHandler(DroneManifest manifest, Queue<Observer> ingress, Queue<Action> egress) {
        ControllerHandler handler = new SkuLd001();

        handler.setEgress(egress);
        handler.setIngress(ingress);
        return handler;
    }
}
