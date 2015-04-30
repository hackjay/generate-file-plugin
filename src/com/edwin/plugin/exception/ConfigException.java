package com.edwin.plugin.exception;

/**
 * 配置异常
 *
 * Created By User: edwin. Time: 15-4-22 15:02.
 */
public class ConfigException extends Exception {

    private static final long serialVersionUID = -7888844392394680412L;

    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public Throwable getRootCause() {
        Throwable rootCause = null;
        Throwable cause = getCause();
        while (cause != null && cause != rootCause) {
            rootCause = cause;
            cause = cause.getCause();
        }
        return rootCause;
    }
}
