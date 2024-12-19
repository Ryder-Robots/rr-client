package org.ryderrobot.models.controllers;

/**
 * Provides an interface for the controller to drone.
 *
 * The idea of this class is that all actions have to be processed at the same to
 * generate certain movement events.
 */
public class ControllerRequest {

    /**
     * Set when scroll up is pressed on controller.
     * @param value true indicates value is set
     */
    public synchronized void setCtlScrollUp(boolean value) {
        ctlScrollUp = value;
    }

    /**
     * Triggered when scroll down is pressed on controller.
     * @param value true if down button is pressed
     */
    public synchronized  void setCtlScrollDown(boolean value) {
        ctlScrollDown = value;
    }

    public synchronized void setScrollRight(boolean value) {
        ctlScrollRight = value;
    }

    public synchronized void setScrollLeft(boolean value) {
        ctlScrollLeft = value;
    }

    // Controller values

    public boolean isCtlScrollUp() {
        return ctlScrollUp;
    }

    public boolean isCtlScrollDown() {
        return ctlScrollDown;
    }

    public boolean isCtlScrollRight() {
        return ctlScrollRight;
    }

    public boolean isCtlScrollLeft() {
        return ctlScrollLeft;
    }

    public synchronized void setCtlXButton(boolean value) {
        ctlXButton = value;
    }

    public boolean isCtlXButton() {
        return ctlXButton;
    }

    public boolean isAxisMoved() {
        return axisMoved;
    }

    public float getAxisXL() {
        return axisXL;
    }

    public float getAxisYL() {
        return axisYL;
    }

    public float getAxisXR() {
        return axisXR;
    }

    public float getAxisYR() {
        return axisYR;
    }

    public synchronized void setAxisXL(float value) {
        axisMoved(true);
        axisXL = value;
    }

    public synchronized void setAxisYL(float value) {
        axisMoved(true);
        axisYL = value;
    }

    public synchronized void setAxisXR(float value) {
        axisMoved(true);
        axisXR = value;
    }

    public synchronized void setAxisYR(float value) {
        axisMoved(true);
        axisYR = value;
    }

    public void reset() {
        setCtlScrollUp(false);
        setCtlScrollDown(false);
        setScrollLeft(false);
        setScrollRight(false);
        axisMoved(false);
        setAxisXL(0);
        setAxisYL(0);
        setAxisXR(0);
        setAxisYR(0);
    }

    private synchronized void axisMoved(boolean value) {
        axisMoved = value;
    }

    private boolean ctlScrollUp = false;
    private boolean ctlScrollDown = false;
    private boolean ctlScrollRight = false;
    private boolean ctlScrollLeft = false;
    private boolean ctlXButton = false;
    private boolean axisMoved = false;

    private float axisXL = 0;
    private float axisYL = 0;
    private float axisXR = 0;
    private float axisYR = 0;
}
