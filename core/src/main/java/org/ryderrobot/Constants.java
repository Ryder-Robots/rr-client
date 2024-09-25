package org.ryderrobot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Constants {
    // applicationName and version be updated on each release.
    public static final String applicationName = "rr-client";
    public static final String applicationVersion = "0.0.1";

    public static final float WORLD_HEIGHT = 1024;
    public static final float WORLD_WIDTH = 1366;
    public static final FileHandle UI_SKIN_ATLAS = Gdx.files.internal("x2/uiskin.atlas");
    public static final FileHandle UI_SKIN = Gdx.files.internal("x2/uiskin.json");

    // Controller constants
    public static final int CTRL_SCROLL_UP = 12;
    public static final int CTRL_SCROLL_DOWN = 11;
    public static final int CTRL_SCROLL_LEFT = 13;
    public static final int CTRL_X_BUTTON = 0;

    // UI Objects
    public static final int ROW_HEIGHT = 60;
    public static final int ROW_WIDTH = 300;
}
