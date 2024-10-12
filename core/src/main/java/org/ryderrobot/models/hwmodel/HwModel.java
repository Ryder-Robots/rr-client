package org.ryderrobot.models.hwmodel;

import java.util.List;

/**
 * Description of drone hardware.
 */

public class HwModel {

    private Dtype dtype;
    private List<Action> actions;
    private List<Observer> observers;

    public Dtype getDtype() {
        return dtype;
    }

    public List<Action> getActions() {
        return actions;
    }

    public List<Observer> getObservers() {
        return observers;
    }
}
