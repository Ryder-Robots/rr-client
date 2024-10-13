package org.ryderrobot.models.hwmodel;

import java.util.List;

/**
 * Description of drone hardware.
 */

public class HwModel {

    private Dtype dtype;
    private List<OpCodes> actions;
    private List<OpCodes> observers;

    public Dtype getDtype() {
        return dtype;
    }

    public List<OpCodes> getActions() {
        return actions;
    }

    public List<OpCodes> getObservers() {
        return observers;
    }
}
