import mode.ConsoleMode;
import model.LogLevel;
import services.LoggingService;

public class Main {
    public static void main(String[] args) {
        LoggingService loggingService = new LoggingService(new ConsoleMode(), LogLevel.INFO);

        loggingService.info("This is info message.");
        loggingService.debug("This is debug message. This will not be printed.");
    }
}
