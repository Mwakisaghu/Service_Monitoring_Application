package services;

import logs.FileLogger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitorServices {
    private final String csvFilePath;
    private final boolean enableFileLogging;
    private final FileLogger fileLogger;  // This should be declared at the class level

    public MonitorServices(String csvFilePath, boolean enableFileLogging, String logFilePath) {
        this.csvFilePath = csvFilePath;
        this.enableFileLogging = enableFileLogging;
        this.fileLogger = new FileLogger(logFilePath);
    }

    // Monitor services at regular intervals
    public void serviceMonitor() {
        // Initialize services scheduler - at fixed intervals
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        // Periodically monitor services in csv
        scheduledExecutorService.scheduleAtFixedRate(this::checkAllServices, 0, 1, TimeUnit.MINUTES);
    }

    // Check all services in the CSV
    private void checkAllServices() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;

            // Skips the CSV header
            bufferedReader.readLine();

            // Looping through each service in the CSV
            while ((line = bufferedReader.readLine()) != null) {
                String[] serviceData = line.split(",");
                String serviceName = serviceData[1];
                String host = serviceData[2];
                int port = Integer.parseInt(serviceData[3]);

                // Check server status and log
                if (isServerReachable(host, port)) {
                    // Checking if the service is UP - connection established to the specified port
                    boolean isServiceUp = isServiceUp(host, port);
                    logServiceStatus(serviceName, host, port, getTimestamp(), isServiceUp);
                } else {
                    // Logging the server as unreachable.
                    logServerStatus(serviceName, host, port, getTimestamp());
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Function to log service status
    private void logServiceStatus(String serviceName, String host, int port, String timestamp, boolean isServiceUp) throws IOException {
        // Log the information and print in a table format
        TablePrinter.printTableHeader();
        TablePrinter.printTableRow(getServiceTableId(), serviceName, Integer.toString(port), timestamp, (isServiceUp ? "UP" : "DOWN"));
        TablePrinter.printTableSeparator();

        // Log actual telnet responses
        String telnetResponse = executeTelnetCommand(host, port);
        TablePrinter.printTableRow("Telnet Response", "", "", "", "");
        TablePrinter.printTableSeparator();
        TablePrinter.printTableRow(telnetResponse, "", "", "", "");
        TablePrinter.printTableSeparator();

        // Log the footer
        TablePrinter.printTableFooter();

        // Log to file if enabled
        if (enableFileLogging) {
            fileLogger.logInfo(serviceName + " - " + (isServiceUp ? "UP" : "DOWN") + " - " + timestamp);
        }
    }

    // Function to log server status
    private void logServerStatus(String serviceName, String host, int port, String timestamp) {
        TablePrinter.printTableHeader();
        TablePrinter.printTableRow(getServiceTableId(), serviceName, Integer.toString(port), timestamp, "UNREACHABLE");
        TablePrinter.printTableSeparator();

        // Logging the footer
        TablePrinter.printTableFooter();

        // Log to file if enabled
        if (enableFileLogging) {
            fileLogger.logWarning(serviceName + " - UNREACHABLE - " + timestamp);
        }
    }

    // Method to generate a service table ID
    private String getServiceTableId() {
        return String.valueOf(System.currentTimeMillis());
    }

    // Checking if the service is up by establishing a socket connection
    private boolean isServiceUp(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Check if the server is reachable using a socket connection
    private boolean isServerReachable(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Execute telnet command - logs the actual telnet responses.
    private String executeTelnetCommand(String host, int port) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("telnet", host, Integer.toString(port));
        Process process = processBuilder.start();

        // Capture telnet command output
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder telnetOutput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                telnetOutput.append(line).append(System.lineSeparator());
            }

            // Wait for the process to finish
            int exitCode;
            try {
                exitCode = process.waitFor();
            } catch (InterruptedException e) {
                throw new IOException("Telnet command interrupted: " + e.getMessage());
            }

            // If the exit code is 0, the telnet command was successful
            return "Exit Code: " + exitCode + System.lineSeparator() + telnetOutput.toString();
        }
    }

    // Getting the timestamp in a specific format
    private String getTimestamp() {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            return dateTimeFormatter.format(now);
        } catch (Exception e) {
            // Handle any exceptions when getting the timestamp
            System.out.println("An error occurred while getting the timestamp: " + e.getMessage());
            return "Timestamp Error";
        }
    }

    // Utility class - for console output layout
    private static class TablePrinter {
        public static void printTableHeader() {
            System.out.println("+------------+-----------------+-------+----------------------+---------------+");
        }

        public static void printTableRow(String... columns) {
            System.out.printf("| %-10s | %-15s | %-5s | %-20s | %-13s |%n", columns);
        }

        public static void printTableSeparator() {
            System.out.println("+------------+-----------------+-------+----------------------+---------------+");
        }

        public static void printTableFooter() {
            System.out.println();
        }
    }
}
