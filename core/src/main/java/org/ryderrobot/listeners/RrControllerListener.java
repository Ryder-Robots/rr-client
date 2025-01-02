package org.ryderrobot.listeners;

import com.badlogic.gdx.controllers.ControllerListener;
import org.ryderrobot.models.controllers.ControllerRequest;

public interface RrControllerListener extends ControllerListener {

    /**
     * is true if movement is detected and there is an active request.
     *
     * @return true on active request.
     */
    boolean isRequest();

    /**
     * Returns last request.
     *
     * @return last request.
     */
    ControllerRequest getRequest();
}
