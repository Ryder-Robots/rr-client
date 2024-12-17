package org.ryderrobot.exceptions;

public class MissingRequiredAttribute extends RuntimeException {
    public MissingRequiredAttribute(String msg) {
        super(msg);
    }
}
