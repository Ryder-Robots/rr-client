package org.ryderrobot.drones;

import org.ryderrobot.models.protocols.rrp.MspIdentPayload;

/**
 * Responsible for creating drone once identity has been retrieved.
 */
public class DroneFactory {

    /**
     * Creates the drone, based on the identity, this should be called
     * if drone.isIdentitySet is true.
     *
     * @param ident current identity
     * @return new drone.
     */
    public static Drone createDrone(MspIdentPayload ident) {
        Drone drone = new VirtualDrone();
        switch(ident.getMspversion()) {
            case VIRTUAL:
                drone = new VirtualDrone();
                break;
            case SKULD001:
                drone = new DroneSdkLd001();
                break;
            case SKULD002:
                drone = new VirtualDrone();
                break;
            default:
                drone = new VirtualDrone();
        }
        drone.setIdent(ident);
        return drone;
    }
}
