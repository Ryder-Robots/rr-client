package org.ryderrobot;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.ryderrobot.client.SocketWriter;
import org.ryderrobot.env.Drone;
import org.ryderrobot.screen.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Texture backgroundText;
    private Viewport viewPort;
    private OrthographicCamera camera;
    private ScreensProcessor screensProcessor;
    private final Drone drone = new Drone();
    private SocketWriter socketWriter;
    private Thread writer;

    @Override
    public void create() {
        // Create the writer socket.
        socketWriter = new SocketWriter(drone);
        writer = new Thread(socketWriter, "socketWriter");
        writer.start();

        TextureAtlas atlas = new TextureAtlas(Constants.UI_SKIN_ATLAS);
        Skin skin = new Skin(Constants.UI_SKIN, atlas);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        viewPort = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        viewPort.apply();
        backgroundText = new Texture("background.png");
        screensProcessor = new ScreensProcessor(backgroundText, viewPort, camera, skin, drone);
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
