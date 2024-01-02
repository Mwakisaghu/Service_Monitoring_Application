package services;

import org.example.CreateCsvFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void serviceMonitor() throws FileNotFoundException {
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
        try {
            // Checking if the server is reachable.
            InetAddress inetAddress = InetAddress.getByName(host);
            boolean isReachable = inetAddress.isReachable(5000);

            try (Socket socket = new Socket(host, port)) {
                // Checking if the service is UP - connection established to the specified port
                if (isReachable) {
                    CreateCsvFile.print(serviceName + " - Status: UP - Timestamp: " + getTimestamp());
                } else {
                    CreateCsvFile.print(serviceName + " - Status: DOWN - Timestamp: " + getTimestamp());
                }
            } catch (IOException e) {
                // Logging the service status as DOWN.
                CreateCsvFile.print(serviceName + " - Status: DOWN - Timestamp: " + getTimestamp());
                e.printStackTrace();
            }
        } catch (IOException e) {
            // Exception to check the reachability
            CreateCsvFile.print("An error occurred while checking the hosts reachability: " + e.getMessage());
            e.printStackTrace();
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
         CreateCsvFile.print("An Error occurred while getting the timestamp: " + e.getMessage());
         e.printStackTrace();
         return "Timestamp Error";
     }
    }
}
