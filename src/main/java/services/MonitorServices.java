package services;

import org.example.CreateCsvFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitorServices {
    private final String csvFilePath;

    // Constructor - initialise the path
    public MonitorServices(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    // Monitor services at regular intervals
    public void serviceMonitor(String csvFilePath) {

        // Initialize services scheduler - at fixed intervals
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        // Periodically monitor services in csv
        scheduledExecutorService.scheduleAtFixedRate(() -> serviceMonitor(csvFilePath), 0, 1, TimeUnit.MINUTES);
    }

    // Monitoring services - in the created csv file
    private void serviceMonitor() throws FileNotFoundException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;

            // Skips the csv header
            bufferedReader.readLine();

            // Looping through each service in the csv
            while ((line = bufferedReader.readLine()) != null) {
                String[] serviceData = line.split(",");
                String serviceName = serviceData[1];
                String host = serviceData[2];
                int port = Integer.parseInt(serviceData[3]);
                String resourceUri = serviceData[4];

                // Record/log status of the current service
                logStatus(serviceName, host, port, resourceUri);
            }

        } catch (IOException e) {
            // Handle errors when reading the csv file
            CreateCsvFile.print("An error occurred while reading the csv file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Function to log statuses of each service
    private void logStatus(String serviceName, String host, int port, String resourceUri) {

    }
}
