package org.ryderrobot.constants;

import com.badlogic.gdx.Gdx;

public class Constants {

    static public int TIMEOUT = 100;
    static public int SLEEP_TIME = 10;

    // End Of Record character.
    static public char EOR = 0x1E;

    public static final String UI_SKIN_ATLAS = "x2/uiskin.atlas";
    public static final String UI_SKIN = "x2/uiskin.json";
    public static final float WORLD_HEIGHT = Gdx.graphics.getBackBufferHeight();
    public static final float WORLD_WIDTH = Gdx.graphics.getBackBufferWidth();

    // UI Objects
    public static final int ROW_HEIGHT = Gdx.graphics.getBackBufferHeight() / 17; // 17th of height
    public static final int ROW_WIDTH = (int) Math.round(Gdx.graphics.getBackBufferWidth() / 4.55); // 4.5

    // Queues
    public static int MAX_QUEUE_COUNT = 100;

    public static String CONNECTION_DETAILS_FL = "etc/droneConn.json";

    // Controller constants
    public static final int CTRL_SCROLL_UP = 12;
    public static final int CTRL_SCROLL_DOWN = 11;
    public static final int CTRL_SCROLL_LEFT = 13;
    public static final int CTRL_SCROLL_RIGHT = 14;
    public static final int CTRL_X_BUTTON = 0;
    public static final int CTRL_AXIS_XL = 0;
    public static final int CTRL_AXIS_YL = 1;
    public static final int CTRL_AXIS_XR = 2;
    public static final int CTRL_AXIS_YR = 3;
}
