package org.ryderrobot.drones;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.ryderrobot.models.protocols.rrp.*;
import org.ryderrobot.net.SocketClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestDrone {

    private final SocketClient socketClient = mock(SocketClient.class);

    @Captor
    private ArgumentCaptor<RrpEvent<?>> argumentCaptor;

    @Test
    public void shouldBeVirtualDrone() {
        Drone drone = DroneFactory.createDrone(new MspIdentPayload(
            0,
            MultiType.NONE,
            MspVersion.VIRTUAL,
            0
        ));
        assertInstanceOf(VirtualDrone.class, drone);
        assertFalse(drone.isIdentitySet());
    }

    @Test
    public void shouldSetCorrectDrone() throws Exception {
        Drone drone = DroneFactory.createDrone(new MspIdentPayload(
            0,
            MultiType.NONE,
            MspVersion.VIRTUAL,
            0
        ));

        when(socketClient.available()).thenReturn(1,0);
        when(socketClient.recv()).thenAnswer(new Answer<RrpEvent>() {
            @Override
            public RrpEvent answer(InvocationOnMock invocation) {
                MspIdentPayload payload = new MspIdentPayload(
                    0,
                    MultiType.NONE,
                    MspVersion.VIRTUAL,
                    0
                );
                RrpEvent<MspIdentPayload> event = new RrpEvent<>(RrpCommands.MSP_IDENT, payload);
                return event;
            }
        });

        drone.setSocketClient(socketClient);
        Thread thread = new Thread(drone, "drone");
        thread.start();
        Thread.sleep(10);
        assertTrue(drone.isRunning());

        drone.setIsRunning(false);
        thread.join();

        assertTrue(drone.isIdentitySet());
        assertFalse(drone.isRunning());

        verify(socketClient).send(argumentCaptor.capture());
    }
}
