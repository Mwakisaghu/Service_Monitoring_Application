package logs;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger {
    private final Logger logger;

    public FileLogger(String logFilePath) {
        logger = Logger.getLogger(FileLogger.class.getName());
        configureFileLogging(logFilePath);
    }

    private void configureFileLogging(String logFilePath) {
        try {
            // Setting up a file handler & formatter
            FileHandler fileHandler = new FileHandler(logFilePath);
            fileHandler.setFormatter(new SimpleFormatter());

            // Adding the file handler to the logger
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while setting up the file handler", e);
        }
    }

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logWarning(String message) {
        logger.warning(message);
    }

    public void logError(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }
}
