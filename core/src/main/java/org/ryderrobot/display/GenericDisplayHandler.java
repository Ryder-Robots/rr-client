package org.ryderrobot.display;

import org.ryderrobot.constants.Constants;

/**
 * Generic display handler, ideally it should handle all displays
 */
public class GenericDisplayHandler implements DisplayHandler {
    private final float worldHeight = Constants.WORLD_HEIGHT;
    private final float worldWidth = Constants.WORLD_WIDTH;
    private final float rowHeight = Constants.ROW_HEIGHT;
    private final float rowWidth = Constants.ROW_WIDTH;

    public GenericDisplayHandler() {
    }

    @Override
    public float worldHeight() {
        return worldHeight;
    }

    @Override
    public float worldWidth() {
        return worldWidth;
    }

    @Override
    public float rowWidth() {
        return rowWidth;
    }

    @Override
    public float rowHeight() {
        return rowHeight;
    }
}
