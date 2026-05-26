package com.dbengine.util;

/**
 * Global constants for the database engine.
 * Centralizes all configuration values used throughout the system.
 *
 * @author Database Engine Project
 * @version 1.0.0
 */
public final class Constants {
    private Constants() {
        // Prevent instantiation
    }

    // ==================== Storage Constants ====================

    /** Size of each page in bytes (4 KB) */
    public static final int PAGE_SIZE = 4096;

    /** Maximum number of pages in buffer pool */
    public static final int BUFFER_POOL_SIZE = 10;

    /** Page header size in bytes */
    public static final int PAGE_HEADER_SIZE = 32;

    /** Maximum number of slots per page */
    public static final int MAX_SLOTS_PER_PAGE = 50;

    /** Database file extension */
    public static final String DB_FILE_EXTENSION = ".db";

    /** Default catalog file name */
    public static final String CATALOG_FILE = "catalog.db";

    // ==================== B-Tree Constants ====================

    /** Order of B-tree (maximum children per node) */
    public static final int BTREE_ORDER = 100;

    /** Minimum degree of B-tree */
    public static final int BTREE_MIN_DEGREE = 50;

    /** Maximum keys per node */
    public static final int BTREE_MAX_KEYS = BTREE_ORDER - 1;

    // ==================== Data Type Sizes ====================

    /** Size of INT type in bytes */
    public static final int INT_SIZE = 4;

    /** Size of LONG type in bytes */
    public static final int LONG_SIZE = 8;

    /** Size of FLOAT type in bytes */
    public static final int FLOAT_SIZE = 4;

    /** Size of DOUBLE type in bytes */
    public static final int DOUBLE_SIZE = 8;

    /** Size of BOOLEAN type in bytes */
    public static final int BOOLEAN_SIZE = 1;

    // ==================== String Constraints ====================

    /** Maximum length for string data */
    public static final int MAX_STRING_LENGTH = 1000;

    /** Maximum length for table name */
    public static final int MAX_TABLE_NAME_LENGTH = 64;

    /** Maximum length for column name */
    public static final int MAX_COLUMN_NAME_LENGTH = 64;

    /** Maximum number of columns per table */
    public static final int MAX_COLUMNS_PER_TABLE = 255;

    // ==================== Transaction Constants ====================

    /** Default transaction timeout in milliseconds */
    public static final long TRANSACTION_TIMEOUT_MS = 30000;

    /** Maximum number of concurrent transactions */
    public static final int MAX_CONCURRENT_TRANSACTIONS = 100;

    /** Lock wait timeout in milliseconds */
    public static final long LOCK_WAIT_TIMEOUT_MS = 5000;

    // ==================== Buffer Pool Constants ====================

    /** LRU cache eviction policy name */
    public static final String CACHE_POLICY_LRU = "LRU";

    // ==================== Success Messages ====================

    /** Generic success message */
    public static final String MSG_SUCCESS = "OK";

    /** Insert successful message template */
    public static final String MSG_INSERT_SUCCESS = "Rows affected: %d";

    /** Update successful message template */
    public static final String MSG_UPDATE_SUCCESS = "Rows affected: %d";

    /** Delete successful message template */
    public static final String MSG_DELETE_SUCCESS = "Rows affected: %d";

    /** Table created successfully message */
    public static final String MSG_TABLE_CREATED = "Table '%s' created successfully";

    // ==================== Error Messages ====================

    /** File not found error */
    public static final String ERR_FILE_NOT_FOUND = "File not found: %s";

    /** Table not found error */
    public static final String ERR_TABLE_NOT_FOUND = "Table not found: %s";

    /** Column not found error */
    public static final String ERR_COLUMN_NOT_FOUND = "Column not found: %s";

    /** Invalid page ID error */
    public static final String ERR_INVALID_PAGE_ID = "Invalid page ID: %d";

    /** Buffer pool full error */
    public static final String ERR_BUFFER_POOL_FULL = "Buffer pool is full";

    /** I/O error */
    public static final String ERR_IO_EXCEPTION = "I/O error: %s";

    /** Parse error */
    public static final String ERR_PARSE_ERROR = "Parse error at line %d, column %d: %s";

    /** Type mismatch error */
    public static final String ERR_TYPE_MISMATCH = "Type mismatch: expected %s, got %s";

    /** Duplicate key error */
    public static final String ERR_DUPLICATE_KEY = "Duplicate key: %s";

    /** Transaction abort error */
    public static final String ERR_TRANSACTION_ABORT = "Transaction aborted: %s";

    /** Lock timeout error */
    public static final String ERR_LOCK_TIMEOUT = "Lock timeout waiting for resource";

    // ==================== Data Type Names ====================

    /** INT data type name */
    public static final String TYPE_INT = "INT";

    /** LONG data type name */
    public static final String TYPE_LONG = "LONG";

    /** FLOAT data type name */
    public static final String TYPE_FLOAT = "FLOAT";

    /** DOUBLE data type name */
    public static final String TYPE_DOUBLE = "DOUBLE";

    /** VARCHAR data type name */
    public static final String TYPE_VARCHAR = "VARCHAR";

    /** BOOLEAN data type name */
    public static final String TYPE_BOOLEAN = "BOOLEAN";

    // ==================== Logging Constants ====================

    /** Log level: DEBUG */
    public static final String LOG_LEVEL_DEBUG = "DEBUG";

    /** Log level: INFO */
    public static final String LOG_LEVEL_INFO = "INFO";

    /** Log level: WARN */
    public static final String LOG_LEVEL_WARN = "WARN";

    /** Log level: ERROR */
    public static final String LOG_LEVEL_ERROR = "ERROR";

    // ==================== Default Values ====================

    /** Default page cache policy */
    public static final String DEFAULT_CACHE_POLICY = CACHE_POLICY_LRU;

    /** Default maximum transaction timeout */
    public static final long DEFAULT_TIMEOUT = TRANSACTION_TIMEOUT_MS;

    /** Default string length for VARCHAR if not specified */
    public static final int DEFAULT_VARCHAR_LENGTH = 255;
}
