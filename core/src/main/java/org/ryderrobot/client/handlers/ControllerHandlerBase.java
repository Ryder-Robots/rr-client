package org.ryderrobot.client.handlers;

import org.ryderrobot.env.Queue;

/**
 * performs any base actions for controller.
 */
public abstract class ControllerHandlerBase implements ControllerHandler {
    protected Queue<String> egress;
    protected Queue<String> ingress;

    @Override
    public void setEgress(Queue<String> egress) {
        this.egress = egress;
    }

    @Override
    public void setIngress(Queue<String> ingress) {
        this.ingress = ingress;
    }
}
