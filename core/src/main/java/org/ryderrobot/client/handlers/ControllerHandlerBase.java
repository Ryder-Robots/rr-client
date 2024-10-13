package org.ryderrobot.client.handlers;

import org.ryderrobot.env.Queue;
import org.ryderrobot.models.hwmodel.Action;
import org.ryderrobot.models.hwmodel.Observer;

public abstract class ControllerHandlerBase implements ControllerHandler {
    protected Queue<Action> egress;
    protected Queue<Observer> ingress;

    @Override
    public void setEgress(Queue<Action> egress) {
        this.egress = egress;
    }

    @Override
    public void setIngress(Queue<Observer> ingress) {
        this.ingress = ingress;
    }
}
