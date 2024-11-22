package mode;

import exceptions.InvalidCommandException;
import models.Command;
import services.ParkingLotService;
import utils.OutputPrinter;
import java.util.Scanner;
import commands.ExitCommandExecutor;

public class InteractiveMode extends Mode {
    public InteractiveMode(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public void process() throws InvalidCommandException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            Command command = new Command(input);
            processCommand(command);

            if (command.getName().equals(ExitCommandExecutor.COMMAND_NAME)) {
                break;
            }
        }
    }
}
