package org.ryderrobot.display;

import com.badlogic.gdx.Gdx;
import org.ryderrobot.constants.Constants;

/**
 * Generic display handler, ideally it should handle all displays
 */
public class GenericDisplayHandler implements DisplayHandler {
    private float worldHeight = Constants.WORLD_HEIGHT;
    private float worldWidth = Constants.WORLD_WIDTH;
    private float rowHeight = Constants.ROW_HEIGHT;
    private float rowWidth = Constants.ROW_WIDTH;

    public GenericDisplayHandler() {
        // compute display if values are available.
        if (Gdx.graphics.getWidth() > 0) {
            worldWidth = Gdx.graphics.getBackBufferWidth();
        }
        if (Gdx.graphics.getHeight() > 0) {
            worldHeight = Gdx.graphics.getBackBufferHeight();
        }
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
