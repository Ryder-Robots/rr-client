package org.ryderrobot.client.handlers;

import org.ryderrobot.env.Queue;

/**
 * Handle events for a controller, this should be implemented for each drone,
 *  </ p>
 * Note that for axis points there are always two values, depending on which axis has moved first.
 * This is to calculate any corresponding movements over the other axis.
 */

public interface ControllerHandler {

    default void setEgress(Queue<String> egress) {}

    default void setIngress(Queue<String> ingress) {}

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
     * @param yValue corresponding movement on the Y axis.
     */
    default void axisLeftX(float value, float yValue) {

    }

    /**
     * called when left axis controller is pushed up.
     *
     * @param value between -1 and 1 for movement of the Y axis
     * @param yValue bewteen -1 and 1 for movement across the y axis
     */
    default void axisLeftY(float value, float yValue) {

    }


    /**
     * called when left axis button X plane
     * @param value between 0 and 1.
     * @param yValue between 0 and 1 for the y axis.
     */
    default void axisRightX(float value, float yValue) {

    }

    /**
     * called when left axis controller is pushed down
     *
     * @param  value between -1 and 1 for movement on the Y axis
     * @param xValue between -1 and 1 for corresponding movement of the X axis
     */
    default void axisRightY(float value, float xValue) {

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
