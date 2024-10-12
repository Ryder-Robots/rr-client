package org.ryderrobot;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.ryderrobot.env.Drone;
import org.ryderrobot.screen.ConnectRobotScreen;
import org.ryderrobot.screen.MainMenuScreen;
import org.ryderrobot.screen.ManualFlightScreen;
import org.ryderrobot.screen.ScreensProcessor;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Texture backgroundText;
    private Viewport viewPort;
    private OrthographicCamera camera;
    private final ScreensProcessor screensProcessor = new ScreensProcessor();
    private final Drone drone = new Drone();


    Array<Screen> screens = new Array<>(true, 2);

    @Override
    public void create() {
        TextureAtlas atlas = new TextureAtlas(Constants.UI_SKIN_ATLAS);
        Skin skin = new Skin(Constants.UI_SKIN, atlas);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        viewPort = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        viewPort.apply();
        backgroundText = new Texture("background.png");

        screens.add(new MainMenuScreen(viewPort, backgroundText, camera, skin, screensProcessor, drone));
        screens.add(new ConnectRobotScreen(viewPort, backgroundText, camera, skin, screensProcessor, drone));
        screens.add(new ManualFlightScreen(viewPort, backgroundText, camera, skin, screensProcessor, drone));

        screens.get(screensProcessor.getCurrScreen()).show();
    }

    @Override
    public void render() {
        if (screensProcessor.hasChanged()) {
            screens.get(screensProcessor.getCurrScreen()).show();
        }
        screens.get(screensProcessor.getCurrScreen()).render(0);
    }

    @Override
    public void dispose() {
        backgroundText.dispose();

        for (Screen s : screens) {
            s.dispose();
        }
    }
}
