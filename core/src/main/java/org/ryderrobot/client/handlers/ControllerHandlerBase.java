package org.ryderrobot.client.handlers;

import com.badlogic.gdx.utils.Json;
import org.ryderrobot.env.Queue;

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
