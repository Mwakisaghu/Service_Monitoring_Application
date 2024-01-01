package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateCsvFile {
    public void createCsvFile(String csvFilePath) {

        // CSV Data
        String[] header = new String[]{"ID", "Service Name", "Service Port", "Service Port", "Service Resource URI",
                "Service Method", "Expected Telnet Response", "Expected Request Response", "Monitoring Intervals",
                "Monitoring Intervals Time Unit"};

        String[] service1Data = new String[]{"1", "Harambee Members Portal", "selfservice.harambeesacco.com",
                "443", "/", "GET", "Connected to selfservice.harambeesacco.com",
                "Welcome to Harambee Sacco Society Ltd Members' Portal", "10", "Minutes"};

        String[] service2Data = new String[]{"2", "Sky World Services Portal", "portal.skyworld.co.ke", "443",
                "/", "GET", "Connected to portal.skyworld.co.ke", "Welcome to Sky World Limited Services Hub", "300",
                "Seconds"};

        // Console Log - App starts
        print("Application started...");

        // Create and write to the CSV file
        try {
            FileWriter csvWriter = new FileWriter(csvFilePath);

            // Writing the header
            writeDataRow(csvWriter, header);

            // Writing data rows
            writeDataRow(csvWriter, service1Data);
            writeDataRow(csvWriter, service2Data);

            // Close the writer
            csvWriter.close();

            // Print CSV creation
            print("CSV file created successfully at: " + csvFilePath);

        } catch (IOException e) {
            System.err.println("Error occurred while creating the CSV file: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void writeDataRow(FileWriter csvWriter, String[] data) throws IOException {
        // Write data items into the csv file
        for (String dataItem : data) {
            csvWriter.append(dataItem);
            csvWriter.append(",");
        }
        csvWriter.append("\n");
    }

    public static void print(String message) {
        // Print messages with timestamp - for the console output
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);
        System.out.println("[" + timestamp + "] " + message);
    }
}