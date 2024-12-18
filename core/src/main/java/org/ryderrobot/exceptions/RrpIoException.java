package org.ryderrobot.exceptions;

import java.io.IOException;

public class RrpIoException extends RrpAbstractException {
    public RrpIoException(IOException ex) {
        super(ex.getMessage());
    }
}
