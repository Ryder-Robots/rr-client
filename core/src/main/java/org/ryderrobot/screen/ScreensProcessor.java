package org.ryderrobot.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.ryderrobot.env.Drone;

public class ScreensProcessor {
    private int currScreen = 0;
    private boolean changed = false;
    private final Array<Screen> screens = new Array<>(true, 2);

    public static int SCR_MM = 0;
    public static int SCR_CONNECT = 1;
    public static int SCR_MAN_FLIGHT = 2;
    public static int SCR_DRONE_DETAILS = 3;

    public ScreensProcessor(
        Texture backgroundText,
        Viewport viewPort,
        OrthographicCamera camera,
        Skin skin,
        Drone drone) {
        //TODO: this should be done in the screen-processor so that the array numbers will always match.
        screens.add(new MainMenuScreen(viewPort, backgroundText, camera, skin, this, drone));
        screens.add(new ConnectRobotScreen(viewPort, backgroundText, camera, skin, this, drone));
        screens.add(new ManualFlightScreen(viewPort, backgroundText, camera, skin, this, drone));
        screens.add(new DroneDetailsScreen(viewPort, backgroundText, camera, skin, this, drone));
    }

    public void setCurrScreen(int screen) {
        changed = true;
        this.currScreen = screen;
    }

    public int getCurrScreen() {
        changed = false;
        return currScreen;
    }

    public boolean hasChanged() {
        return changed;
    }

    public Screen get() {
        return screens.get(getCurrScreen());
    }

    public void dispose() {
        for (Screen s : screens) {
            s.dispose();
        }
    }
}
