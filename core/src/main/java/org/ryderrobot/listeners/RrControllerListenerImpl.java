package org.ryderrobot.listeners;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import org.ryderrobot.constants.Constants;
import org.ryderrobot.models.controllers.ControllerRequest;

public class RrControllerListenerImpl implements RrControllerListener {
    @Override
    public void connected(Controller controller) {
    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int i) {
        movementDetected(controller);
        isRequest = true;
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int i) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int i, float v) {
        movementDetected(controller);
        isRequest = true;
        return true;
    }

    @Override
    public boolean isRequest() {
        boolean result = isRequest;
        isRequest = false;
        return result;
    }

    @Override
    public ControllerRequest getRequest() {
        return request;
    }

    private void movementDetected(Controller controller) {
        request = new ControllerRequest();
        request.setAxisXL(controller.getAxis(Constants.CTRL_AXIS_XL));
        request.setAxisYL(controller.getAxis(Constants.CTRL_AXIS_YL));
        request.setAxisXR(controller.getAxis(Constants.CTRL_AXIS_XR));
        request.setAxisYR(controller.getAxis(Constants.CTRL_AXIS_YR));
        request.setCtlScrollUp(controller.getButton(Constants.CTRL_SCROLL_UP));
        request.setCtlScrollDown(controller.getButton(Constants.CTRL_SCROLL_DOWN));
        request.setScrollLeft(controller.getButton(Constants.CTRL_SCROLL_LEFT));
        request.setScrollRight(controller.getButton(Constants.CTRL_SCROLL_RIGHT));
        request.setCtlXButton(controller.getButton(Constants.CTRL_X_BUTTON));

        isRequest = true;
    }

    private ControllerRequest request;
    private boolean isRequest = false;
}
