package org.ryderrobot.exceptions;

public class RrpInterruptException extends RrpAbstractException {

    public RrpInterruptException(InterruptedException ex) {
        super(ex.getMessage());
    }
}
