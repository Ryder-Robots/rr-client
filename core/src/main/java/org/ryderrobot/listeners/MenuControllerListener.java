package org.ryderrobot.listeners;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import org.ryderrobot.Constants;

public class MenuControllerListener implements ControllerListener, EventListener  {
    private final TextButton[] buttons;
    private int currButtonIdx = 0;

    /*
     * @param the attached button.
     * @param what to do if 'X' is pressed.
     */
    public MenuControllerListener(TextButton[] buttons) {
        this.buttons = buttons;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    protected void generateButtonEvent(TextButton button, InputEvent.Type type, int pointer) {
        InputEvent event = new InputEvent();
        event.setType(type);
        event.setStageX(button.getX());
        event.setStageY(button.getY());
        event.setListenerActor(button);
        event.setPointer(pointer);
        Array<EventListener> eventListeners = button.getListeners();
        for (EventListener el : eventListeners) {
            el.handle(event);
        }
    }

    public boolean buttonDownEvent(Controller controller, int i) {
        return false;
    }

    @Override
    public boolean buttonDown(Controller controller, int i) {
        if (i == Constants.CTRL_SCROLL_UP || i == Constants.CTRL_SCROLL_DOWN) {
            int buttonIdx = currButtonIdx;
            if (i == Constants.CTRL_SCROLL_UP) {
                buttonIdx++;
                if (buttonIdx >= (buttons.length)) {
                    buttonIdx = 0;
                }
            } else {
                buttonIdx--;
                if (buttonIdx <= -1) {
                    buttonIdx = buttons.length - 1;
                }
            }
            generateButtonEvent(buttons[buttonIdx], InputEvent.Type.enter, -1);
            generateButtonEvent(buttons[currButtonIdx], InputEvent.Type.exit, -1);

            currButtonIdx = buttonIdx;
            return true;
        }
        return buttonDownEvent(controller, i);
    }

    @Override
    public boolean buttonUp(Controller controller, int i) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int i, float v) {
        return false;
    }

    // handling events should be exactly the same as if a mouse or keyboard has done it.
    // so just handle the event the same way.
    @Override
    public boolean handle(Event event) {
        return false;
    }

    public final int getCurrButtonIdx() {
        return currButtonIdx;
    }
}
