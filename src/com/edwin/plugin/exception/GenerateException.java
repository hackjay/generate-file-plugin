package com.edwin.plugin.exception;

/**
 * Created By User: edwin. Time: 15-4-23 11:00.
 */
public class GenerateException extends Exception {

    private static final long serialVersionUID = 5978347000837002657L;

    public GenerateException() {
        super();
    }

    public GenerateException(String message) {
        super(message);
    }

    public GenerateException(Throwable cause) {
        super(cause);
    }

    public GenerateException(String message, Throwable cause) {
        super(message, cause);
    }
}
