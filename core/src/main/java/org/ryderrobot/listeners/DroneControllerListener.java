package org.ryderrobot.listeners;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import org.ryderrobot.Constants;
import org.ryderrobot.client.handlers.ControllerHandler;
import org.ryderrobot.client.env.Drone;

/**
 * Listens for controller events
 */

public class DroneControllerListener implements ControllerListener {
    private final ControllerHandler handler;
    private final Drone drone;

    // controller has some weird calibration, also want to stop too much
    // chatter so keeping these in class.
    private float axisXL = 0;
    private float axisYL = 0;
    private float axisXR = 0;
    private float axisYR = 0;

    public DroneControllerListener(ControllerHandler handler, Drone drone) {
        this.handler = handler;
        this.drone = drone;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int i) {
        switch(i) {
            case Constants.CTRL_SCROLL_UP:
                handler.scrollUp();
                break;
            case Constants.CTRL_SCROLL_DOWN:
                handler.scrollDown();
                break;
            case Constants.CTRL_SCROLL_LEFT:
                handler.scrollRight();
                break;
            case Constants.CTRL_X_BUTTON:
                handler.buttonCrossPressed();
                break;
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int i) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int i, float v) {
        switch (i) {
            case Constants.CTRL_AXIS_XL:
                if (axisXL != v) {
                    handler.axisLeftX(v);
                    axisXL = v;
                }
                break;
            case Constants.CTRL_AXIS_YL:
                if (axisYL != v) {
                    handler.axisLeftY(v);
                    axisYL = v;
                }
                break;
            case Constants.CTRL_AXIS_XR:
                if (axisXR != v) {
                    handler.axisLeftY(v);
                    axisXR = v;
                }
                break;
            case Constants.CTRL_AXIS_YR:
                if (axisYR != v) {
                    handler.axisRightY(v);
                    axisYR = v;
                }
                break;
        }
        return false;
    }
}
