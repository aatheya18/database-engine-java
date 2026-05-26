package com.dbengine.exception;

/**
 * Exception for SQL parsing errors.
 * Thrown when query parsing fails with location information.
 *
 * @author Database Engine Project
 * @version 1.0.0
 */
public class ParseException extends DBException {
    private static final long serialVersionUID = 1L;

    /** Line number where error occurred */
    private int line = -1;

    /** Column number where error occurred */
    private int column = -1;

    /**
     * Constructs a ParseException with no arguments.
     */
    public ParseException() {
        super();
    }

    /**
     * Constructs a ParseException with a detail message.
     *
     * @param message the detail message
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Constructs a ParseException with a detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a ParseException with line and column information.
     *
     * @param message the detail message
     * @param line the line number
     * @param column the column number
     */
    public ParseException(String message, int line, int column) {
        super(message);
        this.line = line;
        this.column = column;
    }

    /**
     * Constructs a ParseException with line, column, and error code.
     *
     * @param message the detail message
     * @param line the line number
     * @param column the column number
     * @param errorCode the error code
     */
    public ParseException(String message, int line, int column, String errorCode) {
        super(message, errorCode);
        this.line = line;
        this.column = column;
    }

    /**
     * Get the line number where error occurred.
     *
     * @return the line number, or -1 if not set
     */
    public int getLine() {
        return line;
    }

    /**
     * Set the line number.
     *
     * @param line the line number
     */
    public void setLine(int line) {
        this.line = line;
    }

    /**
     * Get the column number where error occurred.
     *
     * @return the column number, or -1 if not set
     */
    public int getColumn() {
        return column;
    }

    /**
     * Set the column number.
     *
     * @param column the column number
     */
    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String getMessage() {
        String baseMessage = super.getMessage();
        if (line > 0 || column > 0) {
            return String.format("%s (line %d, column %d)", baseMessage, line, column);
        }
        return baseMessage;
    }
}
