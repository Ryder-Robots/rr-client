package org.ryderrobot.client.handlers;

import org.ryderrobot.client.env.Drone;

/**
 * Handle events for a controller, this should be implemented for each drone,
 */

public interface ControllerHandler {

    /**
     * called when up button is pushed.
     */
    default void scrollUp() {

    }

    /**
     * called when the down button is pressed
     */
    default void scrollDown() {

    }

    /**
     * called when left button is pushed.
     */
    default void scrollLeft() {

    }

    /**
     * called when right button is pushed
     */
    default void scrollRight() {

    }

    /**
     * called when left axis button X plane
     * @param value between 0 and 1.
     */
    default void axisLeftX(float value) {

    }

    /**
     * called when left axis controller is pushed up.
     */
    default void axisLeftY(float value) {

    }


    /**
     * called when left axis button X plane
     * @param value between 0 and 1.
     */
    default void axisRightX(Drone drone, float value) {

    }

    /**
     * called when left axis controller is pushed down
     */
    default void axisRightY(float value) {

    }

    /**
     * square button has been pressed.
     */
    default void buttonSquarePressed() {

    }

    /**
     * triangle button pressed.
     */
    default void buttonTrianglePressed() {

    }

    /**
     * circle button pressed.
     */
    default void buttonCirclePressed() {

    }

    /**
     * Cross button has been pressed.
     */
    default void buttonCrossPressed() {

    }
}
