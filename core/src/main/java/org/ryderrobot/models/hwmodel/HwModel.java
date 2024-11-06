package org.ryderrobot.models.hwmodel;

import java.util.List;
import java.util.Map;

/**
 * Description of drone hardware.
 */

public class HwModel {

    private Dtype dtype;
    private List<OpCodes> actions;
    private List<OpCodes> observers;
    private Mc mc;

    public Dtype getDtype() {
        return dtype;
    }

    public List<OpCodes> getActions() {
        return actions;
    }

    public List<OpCodes> getObservers() {
        return observers;
    }

    public Mc getMc() {
        return mc;
    }
}
