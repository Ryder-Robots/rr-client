package org.ryderrobot.drones;

import org.ryderrobot.constants.Constants;
import org.ryderrobot.models.protocols.rrp.*;
import org.ryderrobot.net.SocketClient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractDrone implements Drone, Runnable {

    @Override
    public final SocketClient getSocketClient() {
        return socketClient;
    }

    @Override
    public void dispose() {
        socketClient.close();
    }

    @Override
    public final boolean isConnected() {
        return connected;
    }

    @Override
    public final void setIsConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public final MspIdentPayload getIdentity() {
        return ident;
    }

    @Override
    public final MspStatus getStatus() {
        return status;
    }

    /**
     * Ran within the thread interact with queue.
     */
    @Override
    public void run() {
        if (!outbound.isEmpty()) {
            int count = Math.min(Constants.MAX_QUEUE_COUNT, outbound.size());
            for (int i = 0; i < count; i++) {
                RrpEvent<?> event = outbound.poll();
                socketClient.send(event);
            }
        }
        RrpEvent<?> statusRequest = new RrpEvent<>(RrpCommands.MSP_STATUS);
        socketClient.send(statusRequest);

        if (socketClient.available() > 0) {
            int count = Math.min(Constants.MAX_QUEUE_COUNT, socketClient.available());
            for (int i = 0; i < count; i++) {
                RrpEvent<?> event = socketClient.recv();

                if(!event.hasPayload()) {
                    continue;
                }

                switch (event.getCommand()) {
                    case MSP_STATUS:
                        this.status = (MspStatus) event.getPayload();
                        break;
                    case MSP_IDENT:
                        this.ident = (MspIdentPayload) event.getPayload();
                        break;
                    default:
                        System.out.println("ignoring command:" + event.getCommand());
                }
            }
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private final BlockingQueue<RrpEvent<?>> outbound = new LinkedBlockingQueue<>();
    private final SocketClient socketClient = new SocketClient();
    private boolean connected = false;

    // Gets reset periodically.
    private MspIdentPayload ident = new MspIdentPayload(
        0,
        MultiType.NONE,
        MspVersion.VIRTUAL,
        0
    );

    private MspStatus status = new MspStatus(0, 0, 0, 0, 0);

}
