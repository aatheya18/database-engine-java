package com.dbengine.exception;

/**
 * Exception for storage layer errors.
 * Thrown when file I/O, buffer pool, or page management operations fail.
 *
 * @author Database Engine Project
 * @version 1.0.0
 */
public class StorageException extends DBException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a StorageException with no arguments.
     */
    public StorageException() {
        super();
    }

    /**
     * Constructs a StorageException with a detail message.
     *
     * @param message the detail message
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Constructs a StorageException with a detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a StorageException with a cause.
     *
     * @param cause the cause of this exception
     */
    public StorageException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a StorageException with a detail message and error code.
     *
     * @param message the detail message
     * @param errorCode the error code
     */
    public StorageException(String message, String errorCode) {
        super(message, errorCode);
    }
}
