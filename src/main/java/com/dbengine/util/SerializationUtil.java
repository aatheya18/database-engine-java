package com.dbengine.util;

/**
 * Serialization utility for converting between Java types and byte arrays.
 * Supports all primitive types and strings using big-endian byte order.
 *
 * @author Database Engine Project
 * @version 1.0.0
 */
public final class SerializationUtil {
    private SerializationUtil() {
        // Prevent instantiation
    }

    // ==================== Integer Serialization ====================

    /**
     * Convert an integer to a 4-byte array (big-endian).
     *
     * @param value the integer value
     * @return 4-byte array representation
     */
    public static byte[] intToBytes(int value) {
        return new byte[]{
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

    /**
     * Convert bytes to an integer (big-endian).
     *
     * @param data byte array
     * @param offset starting offset in array
     * @return integer value
     */
    public static int bytesToInt(byte[] data, int offset) {
        return ((data[offset] & 0xFF) << 24) |
                ((data[offset + 1] & 0xFF) << 16) |
                ((data[offset + 2] & 0xFF) << 8) |
                (data[offset + 3] & 0xFF);
    }

    // ==================== Long Serialization ====================

    /**
     * Convert a long to an 8-byte array (big-endian).
     *
     * @param value the long value
     * @return 8-byte array representation
     */
    public static byte[] longToBytes(long value) {
        return new byte[]{
                (byte) ((value >> 56) & 0xFF),
                (byte) ((value >> 48) & 0xFF),
                (byte) ((value >> 40) & 0xFF),
                (byte) ((value >> 32) & 0xFF),
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

    /**
     * Convert bytes to a long (big-endian).
     *
     * @param data byte array
     * @param offset starting offset in array
     * @return long value
     */
    public static long bytesToLong(byte[] data, int offset) {
        return ((long) data[offset] & 0xFF) << 56 |
                ((long) data[offset + 1] & 0xFF) << 48 |
                ((long) data[offset + 2] & 0xFF) << 40 |
                ((long) data[offset + 3] & 0xFF) << 32 |
                ((long) data[offset + 4] & 0xFF) << 24 |
                ((long) data[offset + 5] & 0xFF) << 16 |
                ((long) data[offset + 6] & 0xFF) << 8 |
                (long) data[offset + 7] & 0xFF;
    }

    // ==================== Float Serialization ====================

    /**
     * Convert a float to a 4-byte array.
     *
     * @param value the float value
     * @return 4-byte array representation
     */
    public static byte[] floatToBytes(float value) {
        return intToBytes(Float.floatToIntBits(value));
    }

    /**
     * Convert bytes to a float.
     *
     * @param data byte array
     * @param offset starting offset in array
     * @return float value
     */
    public static float bytesToFloat(byte[] data, int offset) {
        return Float.intBitsToFloat(bytesToInt(data, offset));
    }

    // ==================== Double Serialization ====================

    /**
     * Convert a double to an 8-byte array.
     *
     * @param value the double value
     * @return 8-byte array representation
     */
    public static byte[] doubleToBytes(double value) {
        return longToBytes(Double.doubleToLongBits(value));
    }

    /**
     * Convert bytes to a double.
     *
     * @param data byte array
     * @param offset starting offset in array
     * @return double value
     */
    public static double bytesToDouble(byte[] data, int offset) {
        return Double.longBitsToDouble(bytesToLong(data, offset));
    }

    // ==================== Boolean Serialization ====================

    /**
     * Convert a boolean to a 1-byte array.
     *
     * @param value the boolean value
     * @return 1-byte array (0x00 for false, 0x01 for true)
     */
    public static byte[] booleanToBytes(boolean value) {
        return new byte[]{(byte) (value ? 0x01 : 0x00)};
    }

    /**
     * Convert a byte to a boolean.
     *
     * @param data byte array
     * @param offset starting offset in array
     * @return boolean value
     */
    public static boolean bytesToBoolean(byte[] data, int offset) {
        return data[offset] != 0x00;
    }

    // ==================== String Serialization ====================

    /**
     * Convert a string to a byte array with length prefix.
     * Format: [length (4 bytes)][UTF-8 string bytes]
     *
     * @param value the string value
     * @return byte array with length prefix
     */
    public static byte[] stringToBytes(String value) {
        if (value == null) {
            return intToBytes(-1); // -1 indicates null string
        }

        byte[] stringBytes = value.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        byte[] result = new byte[Constants.INT_SIZE + stringBytes.length];
        System.arraycopy(intToBytes(stringBytes.length), 0, result, 0, Constants.INT_SIZE);
        System.arraycopy(stringBytes, 0, result, Constants.INT_SIZE, stringBytes.length);
        return result;
    }

    /**
     * Convert bytes to a string (expects length prefix).
     *
     * @param data byte array with length prefix
     * @param offset starting offset in array
     * @return string value, or null if length is -1
     */
    public static String bytesToString(byte[] data, int offset) {
        int length = bytesToInt(data, offset);
        if (length == -1) {
            return null;
        }
        return new String(data, offset + Constants.INT_SIZE, length, 
                java.nio.charset.StandardCharsets.UTF_8);
    }

    /**
     * Calculate the total bytes needed to serialize a string with length prefix.
     *
     * @param value the string value
     * @return total bytes needed
     */
    public static int stringBytesLength(String value) {
        if (value == null) {
            return Constants.INT_SIZE;
        }
        return Constants.INT_SIZE + value.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;
    }

    // ==================== Null Handling ====================

    /**
     * Check if a byte represents NULL.
     *
     * @param data byte array
     * @param offset offset in array
     * @return true if null marker
     */
    public static boolean isNull(byte[] data, int offset) {
        return data[offset] == (byte) 0xFF;
    }

    /**
     * Write a NULL marker.
     *
     * @param data byte array
     * @param offset offset in array
     */
    public static void writeNull(byte[] data, int offset) {
        data[offset] = (byte) 0xFF;
    }

    // ==================== Hex Utilities (for debugging) ====================

    /**
     * Convert byte array to hex string for debugging.
     *
     * @param data byte array
     * @return hex string representation
     */
    public static String toHexString(byte[] data) {
        if (data == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Convert byte array to readable string with ASCII representation.
     *
     * @param data byte array
     * @return readable string
     */
    public static String toReadableString(byte[] data) {
        if (data == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Hex: ");
        for (byte b : data) {
            sb.append(String.format("%02x ", b));
        }
        sb.append("\nASCII: ");
        for (byte b : data) {
            if (b >= 32 && b < 127) {
                sb.append((char) b);
            } else {
                sb.append(".");
            }
        }
        return sb.toString();
    }
}
