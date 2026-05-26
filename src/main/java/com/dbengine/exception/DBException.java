package com.dbengine.exception;

/**
 * Base exception for all database engine errors.
 * All specific exceptions should extend this class.
 *
 * @author Database Engine Project
 * @version 1.0.0
 */
public class DBException extends Exception {
    private static final long serialVersionUID = 1L;

    /** Optional error code */
    private String errorCode;

    /**
     * Constructs a DBException with no arguments.
     */
    public DBException() {
        super();
    }

    /**
     * Constructs a DBException with a detail message.
     *
     * @param message the detail message
     */
    public DBException(String message) {
        super(message);
    }

    /**
     * Constructs a DBException with a detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a DBException with a cause.
     *
     * @param cause the cause of this exception
     */
    public DBException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a DBException with a detail message and error code.
     *
     * @param message the detail message
     * @param errorCode the error code
     */
    public DBException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Get the error code if set.
     *
     * @return the error code, or null if not set
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Set the error code.
     *
     * @param errorCode the error code to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
