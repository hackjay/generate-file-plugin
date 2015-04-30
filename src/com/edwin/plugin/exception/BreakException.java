package com.edwin.plugin.exception;

/**
 * Created By User: edwin. Time: 15-4-23 13:20.
 */
public class BreakException extends Exception {

    private static final long serialVersionUID = -622666994174072772L;

    public BreakException() {
        super();
    }

    public BreakException(String message) {
        super(message);
    }

    public BreakException(Throwable cause) {
        super(cause);
    }

    public BreakException(String message, Throwable cause) {
        super(message, cause);
    }
}
