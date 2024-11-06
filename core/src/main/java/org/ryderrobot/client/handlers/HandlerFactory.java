package org.ryderrobot.client.handlers;

import org.ryderrobot.env.Drone;
import org.ryderrobot.env.Queue;
import org.ryderrobot.models.DroneManifest;

/**
 * When given a drone manifest returns the correct handler
 */

public class HandlerFactory {
    public static ControllerHandler getHandler(DroneManifest manifest, Queue<String> ingress, Queue<String> egress, Drone drone) {
        ControllerHandler handler;

        String droneType = manifest.getHwmodel().getDtype().getValue();
        if (droneType.equals("ldsku001")) {
            handler = new SkuLd001(drone);
        } else if (droneType.equals("skuld002")) {
            handler = new SkuLd002(drone);
        } else {
            // Assume virtual. For now this is the same as SKU-LD-001, that can change in the
            // future versions.
            handler = new SkuLd001(drone);
        }

        handler.setEgress(egress);
        handler.setIngress(ingress);
        return handler;
    }
}
