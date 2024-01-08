package Main;

import org.example.CreateCsvFile;
import services.MonitorServices;

import java.io.FileNotFoundException;

public class MainApp {

    public static void main(String[] args) throws FileNotFoundException {
        // Path to the csv file
        String csvFilePath = "/home/hmc/Documents/services.csv";

        // Creating the CSV file
        CreateCsvFile createCsvFile = new CreateCsvFile();
        createCsvFile.createCsvFile(csvFilePath);

        // Monitoring the services
        MonitorServices monitorServices = new MonitorServices(csvFilePath);

        monitorServices.serviceMonitor();
    }
}






