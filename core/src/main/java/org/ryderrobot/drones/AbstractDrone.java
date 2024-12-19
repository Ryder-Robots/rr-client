package org.ryderrobot.drones;

import org.ryderrobot.constants.Constants;
import org.ryderrobot.models.protocols.rrp.*;
import org.ryderrobot.net.SocketClient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractDrone implements Drone {

    @Override
    public void setSocketClient(final SocketClient socketClient) {
        this.socketClient = socketClient;
    }

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

    @Override
    public final boolean isIdentitySet() {
        return isIdentSet;
    }

    @Override
    public synchronized void setIdent(MspIdentPayload ident) {
        this.ident = ident;
    }

    private synchronized void setIdentSet(boolean value) {
        isIdentSet = value;
    }

    /**
     * Ran within the thread interact with queue.
     */
    @Override
    public void run() {
        while(isRunning) {
            if (!outbound.isEmpty()) {
                int count = Math.min(Constants.MAX_QUEUE_COUNT, outbound.size());
                for (int i = 0; i < count; i++) {
                    RrpEvent<?> event = outbound.poll();
                    socketClient.send(event);
                }
            }
            RrpEvent<?> statusRequest = new RrpEvent<>(RrpCommands.MSP_STATUS);
            socketClient.send(statusRequest);

            int count = socketClient.available();
            if (count > 0) {
                count = Math.min(Constants.MAX_QUEUE_COUNT, count);
                for (int i = 0; i < count; i++) {
                    RrpEvent<?> event = socketClient.recv();

                    if (!event.hasPayload()) {
                        continue;
                    }

                    switch (event.getCommand()) {
                        case MSP_STATUS:
                            this.status = (MspStatus) event.getPayload();
                            break;
                        case MSP_IDENT:
                            setIdent((MspIdentPayload) event.getPayload());
                            setIdentSet(true);
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
    }

    @Override
    public final boolean isRunning() {
        return isRunning;
    }

    @Override
    public synchronized void setIsRunning(boolean value) {
        isRunning = value;
    }

    private final BlockingQueue<RrpEvent<?>> outbound = new LinkedBlockingQueue<>();
    private SocketClient socketClient;
    private boolean connected = false;

    // Gets reset periodically.
    private MspIdentPayload ident = new MspIdentPayload(
        0,
        MultiType.NONE,
        MspVersion.VIRTUAL,
        0
    );

    private MspStatus status = new MspStatus(0, 0, 0, 0, 0);
    private boolean isIdentSet = false;
    private boolean isRunning = true;
}
