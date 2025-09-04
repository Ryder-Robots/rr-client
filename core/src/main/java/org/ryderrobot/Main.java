package org.ryderrobot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.ryderrobot.constants.Constants;
import org.ryderrobot.display.DisplayHandler;
import org.ryderrobot.display.GenericDisplayHandler;
import org.ryderrobot.drones.Drone;
import org.ryderrobot.drones.VirtualDrone;
import org.ryderrobot.models.protocols.rrp.MspIdentPayload;
import org.ryderrobot.net.SocketClient;
import org.ryderrobot.net.TcpSocketClient;
import org.ryderrobot.screens.ScreensProcessor;

import static org.ryderrobot.models.protocols.rrp.MspVersion.VIRTUAL;
import static org.ryderrobot.models.protocols.rrp.MultiType.NONE;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Texture backgroundText;
    private final Drone drone = new VirtualDrone();
    private ScreensProcessor screensProcessor;
    private final SocketClient socketClient = new TcpSocketClient();

    @Override
    public void create() {
        drone.setSocketClient(socketClient);
        drone.setIdent(new MspIdentPayload(0, NONE, VIRTUAL, 0));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(Constants.UI_SKIN_ATLAS));
        Skin skin = new Skin( Gdx.files.internal(Constants.UI_SKIN), atlas);
        DisplayHandler displayHandler = new GenericDisplayHandler();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, displayHandler.worldWidth(), displayHandler.worldHeight());
        Viewport viewPort = new FitViewport(displayHandler.worldWidth(), displayHandler.worldHeight(), camera);
        viewPort.apply();
        backgroundText = new Texture("background.png");
        screensProcessor = new ScreensProcessor(backgroundText, viewPort, camera, skin, drone, displayHandler);
        screensProcessor.get().show();
    }

    @Override
    public void render() {
        if (screensProcessor.hasChanged()) {
            screensProcessor.get().show();
        }
        screensProcessor.get().render(0);
    }

    @Override
    public void dispose() {
        backgroundText.dispose();
        screensProcessor.dispose();
    }
}
