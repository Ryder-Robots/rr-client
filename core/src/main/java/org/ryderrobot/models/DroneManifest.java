package org.ryderrobot.models;

import org.ryderrobot.models.hwmodel.HwModel;

/**
 * description of the drone.
 */

public class DroneManifest {
    private String description;
    private String swname;
    private String swversion;
    private HwModel hwmodel;

    public String getDescription() {
        return description;
    }

    public String getSwname() {
        return swname;
    }

    public String getSwversion() {
        return swversion;
    }

    public HwModel getHwmodel() {
        return hwmodel;
    }
}
