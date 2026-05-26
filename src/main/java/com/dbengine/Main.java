package com.dbengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Main entry point for the Database Engine.
 * Provides an interactive REPL (Read-Eval-Print Loop) for executing queries.
 *
 * @author Database Engine Project
 * @version 1.0.0
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String PROMPT = "db> ";
    private static final String WELCOME_MESSAGE = "Database Engine v1.0.0\n" +
            "Type 'help' for available commands or 'exit' to quit.\n";

    public static void main(String[] args) {
        logger.info("Starting Database Engine...");
        System.out.println(WELCOME_MESSAGE);

        try (Scanner scanner = new Scanner(System.in)) {
            String input;
            while (true) {
                System.out.print(PROMPT);
                input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    continue;
                }

                if (!executeCommand(input)) {
                    break; // Exit loop if user enters 'exit'
                }
            }
        } catch (Exception e) {
            logger.error("Unexpected error in main loop", e);
            System.err.println("Error: " + e.getMessage());
        }

        logger.info("Database Engine shutdown");
        System.out.println("Goodbye!");
    }

    /**
     * Execute user command.
     *
     * @param command the user input command
     * @return false if user wants to exit, true otherwise
     */
    private static boolean executeCommand(String command) {
        String lowerCommand = command.toLowerCase();

        if (lowerCommand.equals("exit") || lowerCommand.equals("quit")) {
            return false;
        }

        if (lowerCommand.equals("help")) {
            printHelp();
            return true;
        }

        if (lowerCommand.equals("status")) {
            printStatus();
            return true;
        }

        if (lowerCommand.startsWith("select") || lowerCommand.startsWith("insert") ||
                lowerCommand.startsWith("update") || lowerCommand.startsWith("delete") ||
                lowerCommand.startsWith("create")) {
            executeQuery(command);
            return true;
        }

        System.out.println("Unknown command: " + command);
        System.out.println("Type 'help' for available commands.");
        return true;
    }

    /**
     * Execute SQL query.
     *
     * @param query the SQL query to execute
     */
    private static void executeQuery(String query) {
        logger.debug("Executing query: {}", query);
        try {
            // TODO: Implement query execution
            System.out.println("Query execution not yet implemented.");
            System.out.println("Query: " + query);
        } catch (Exception e) {
            logger.error("Error executing query", e);
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Print help message.
     */
    private static void printHelp() {
        System.out.println("\n=== Available Commands ===");
        System.out.println("SQL Operations:");
        System.out.println("  SELECT * FROM table_name [WHERE condition];");
        System.out.println("  INSERT INTO table_name VALUES (value1, value2, ...);");
        System.out.println("  UPDATE table_name SET column = value [WHERE condition];");
        System.out.println("  DELETE FROM table_name [WHERE condition];");
        System.out.println("  CREATE TABLE table_name (column_name type, ...);");
        System.out.println();
        System.out.println("Utility Commands:");
        System.out.println("  help    - Show this help message");
        System.out.println("  status  - Show system status");
        System.out.println("  exit    - Exit the database engine");
        System.out.println();
    }

    /**
     * Print system status.
     */
    private static void printStatus() {
        System.out.println("\n=== Database Engine Status ===");
        System.out.println("Version: 1.0.0");
        System.out.println("Status: Running");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Available Memory: " + Runtime.getRuntime().totalMemory() / (1024 * 1024) + " MB");
        System.out.println();
    }
}
