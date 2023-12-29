# Overview
A Java-based Service Monitoring Application which allows you to monitor multiple services independently, logging their status and server reachability at defined intervals. The application reads service details from a CSV file and performs monitoring checks accordingly.

Getting Started
# Prerequisites
- Java Development Kit (JDK) installed on your system.
- Git to clone the repository.
  
# Installation
- Clone the repository:
  use "git clone" command.
Navigate to the project directory:

# Usage
- Compile the Java file
  for example : "javac ServiceMonitor.java"

- Run the application, providing the path to your CSV file:
  Example : "java ServiceMonitor --csvFile /path/to/your/file.csv"

# CSV File Format
Create a CSV file with the following fields for each service:

- ID: Unique identifier for the service.
- Service Name: Name of the service.
- Service Host: Hostname or IP address of the server hosting the service.
- Service Port: Port number on which the service is running.
- Service Resource URI: URI or path for the service resource.
- Service Method: HTTP method for service communication (e.g., GET).
- Expected Telnet Response: Expected response when connecting via Telnet.
- Expected Request Response: Expected response when sending a request to the service.
- Monitoring Intervals: Time between monitoring checks.
- Monitoring Intervals Time Unit: Unit of time for monitoring intervals (e.g., Minutes, Seconds).

# Logging
The application logs the status of each service with timestamps for reference. Logs include:

- Timestamp: Date and time when the status was logged.
- Service ID: Unique identifier for the service.
- Service Name: Name of the service.
- Service Status: Indicates whether the service is up or down.
- Server Reachability: Indicates whether the server hosting the service is reachable.

# Customization
Feel free to customize the application to include additional features, suggestions and also reach out for collaborations.
