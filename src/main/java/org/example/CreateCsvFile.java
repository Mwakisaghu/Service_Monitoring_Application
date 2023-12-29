package org.example;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.println;

public class CreateCsvFile {
    public static void main(String[] args) {
        // Path to store csv file
        String csvFilePath = "/home/hmc/Documents/services.csv";

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
        println("Application started");
    }
}