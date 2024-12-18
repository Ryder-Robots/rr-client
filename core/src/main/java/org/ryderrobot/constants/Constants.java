package org.ryderrobot.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Constants {

    static public int TIMEOUT = 100;
    static public int SLEEP_TIME = 10;

    // End Of Record chaacter.
    static public char EOR = 0x1E;

    public static final FileHandle UI_SKIN_ATLAS = Gdx.files.internal("x2/uiskin.atlas");
    public static final FileHandle UI_SKIN = Gdx.files.internal("x2/uiskin.json");
    public static final float WORLD_HEIGHT = 1024;
    public static final float WORLD_WIDTH = 1366;

    // UI Objects
    public static final int ROW_HEIGHT = 60;
    public static final int ROW_WIDTH = 300;
}
