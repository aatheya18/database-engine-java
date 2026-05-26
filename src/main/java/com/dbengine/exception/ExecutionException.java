package com.dbengine.exception;

/**
 * Exception for query execution errors.
 * Thrown when query execution fails during runtime.
 *
 * @author Database Engine Project
 * @version 1.0.0
 */
public class ExecutionException extends DBException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an ExecutionException with no arguments.
     */
    public ExecutionException() {
        super();
    }

    /**
     * Constructs an ExecutionException with a detail message.
     *
     * @param message the detail message
     */
    public ExecutionException(String message) {
        super(message);
    }

    /**
     * Constructs an ExecutionException with a detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an ExecutionException with a cause.
     *
     * @param cause the cause of this exception
     */
    public ExecutionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an ExecutionException with a detail message and error code.
     *
     * @param message the detail message
     * @param errorCode the error code
     */
    public ExecutionException(String message, String errorCode) {
        super(message, errorCode);
    }
}
