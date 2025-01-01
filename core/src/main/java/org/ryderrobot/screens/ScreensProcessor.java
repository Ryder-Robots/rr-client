package org.ryderrobot.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.ryderrobot.drones.Drone;

public class ScreensProcessor {

    private int currScreen = 0;
    private boolean changed = false;
    private final Array<Screen> screens = new Array<>(true, 2);
    private Drone drone;

    public static int SCR_MM = 0;
    public static int SCR_CONNECT = 1;
    public static int SCR_MAN_FLIGHT = 2;
    public static int SCR_IDENT = 3;
    public static int SCR_STATUS = 4;

    public ScreensProcessor(
        Texture backgroundText,
        Viewport viewPort,
        OrthographicCamera camera,
        Skin skin,
        Drone drone) {

        this.drone = drone;
        screens.add(new MainMenuScreen(viewPort, backgroundText, camera, skin, this));
        screens.add(new ConnectRobotScreen(viewPort, backgroundText, camera, skin, this));
        screens.add(new ManualFlightScreen());
        screens.add(new DroneDetailsScreen(viewPort, backgroundText, camera, skin, this));
        screens.add(new StatusScreen(viewPort, backgroundText, camera, skin, this));
    }

    public void setCurrScreen(int screen) {
        changed = true;
        this.currScreen = screen;
    }

    public int getCurrScreen() {
        changed = false;
        return currScreen;
    }

    public Screen get() {
        return screens.get(getCurrScreen());
    }

    public boolean hasChanged() {
        return changed;
    }

    public void dispose() {
        for (Screen s : screens) {
            s.dispose();
        }
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }
}
