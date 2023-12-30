package services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitorServices {
    private String csvFilePath;

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
}
