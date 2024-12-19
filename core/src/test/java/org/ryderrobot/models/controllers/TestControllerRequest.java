package org.ryderrobot.models.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestControllerRequest {
    ControllerRequest controllerHandler = new ControllerRequest();

    @Test
    public void shouldSetLeft() {
        controllerHandler.reset();
        controllerHandler.setScrollLeft(true);

        assertTrue(controllerHandler.isCtlScrollLeft());
        assertFalse(controllerHandler.isCtlScrollDown());
        assertFalse(controllerHandler.isCtlScrollUp());
        assertFalse(controllerHandler.isCtlScrollRight());
        assertFalse(controllerHandler.isCtlXButton());
    }

    @Test
    public void shouldSetLeftUp() {
        controllerHandler.reset();
        controllerHandler.setScrollLeft(true);
        controllerHandler.setCtlScrollUp(true);

        assertTrue(controllerHandler.isCtlScrollLeft());
        assertFalse(controllerHandler.isCtlScrollDown());
        assertTrue(controllerHandler.isCtlScrollUp());
        assertFalse(controllerHandler.isCtlScrollRight());
        assertFalse(controllerHandler.isCtlXButton());
    }

    @Test
    public void shouldPressXButton() {
        controllerHandler.reset();
        controllerHandler.setCtlXButton(true);
        assertFalse(controllerHandler.isCtlScrollLeft());
        assertFalse(controllerHandler.isCtlScrollDown());
        assertFalse(controllerHandler.isCtlScrollUp());
        assertFalse(controllerHandler.isCtlScrollRight());
        assertTrue(controllerHandler.isCtlXButton());
    }

    @Test
    public void shouldSetAxis1() {
        controllerHandler.reset();
        controllerHandler.setAxisXL(0.5f);
        controllerHandler.setAxisYL(0.5f);

        assertFalse(controllerHandler.isCtlScrollLeft());
        assertFalse(controllerHandler.isCtlScrollDown());
        assertFalse(controllerHandler.isCtlScrollUp());
        assertFalse(controllerHandler.isCtlScrollRight());

        assertEquals(0.5f, controllerHandler.getAxisXL());
        assertEquals(0.5f, controllerHandler.getAxisYL());
        assertEquals(0, controllerHandler.getAxisXR());
        assertEquals(0, controllerHandler.getAxisYR());

        assertTrue(controllerHandler.isAxisMoved());
    }

    @Test
    public void shouldSetAxis2() {
        controllerHandler.reset();
        controllerHandler.setAxisXR(0.5f);
        controllerHandler.setAxisYR(0.5f);

        assertFalse(controllerHandler.isCtlScrollLeft());
        assertFalse(controllerHandler.isCtlScrollDown());
        assertFalse(controllerHandler.isCtlScrollUp());
        assertFalse(controllerHandler.isCtlScrollRight());

        assertEquals(0, controllerHandler.getAxisXL());
        assertEquals(0, controllerHandler.getAxisYL());
        assertEquals(0.5f, controllerHandler.getAxisXR());
        assertEquals(0.5f, controllerHandler.getAxisYR());

        assertTrue(controllerHandler.isAxisMoved());
    }
}
