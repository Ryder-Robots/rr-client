package org.ryderrobot.screen;

public class ScreensProcessor {
    private int currScreen = 0;
    private boolean changed = false;

    public static int SCR_MM = 0;
    public static int SCR_CONNECT = 1;

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
}
