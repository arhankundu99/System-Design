package commands;

import exceptions.InvalidCommandException;
import models.Command;
import services.ParkingLotService;
import utils.OutputPrinter;

public class CommandExecutorFactory {
    ParkingLotService parkingLotService;
    OutputPrinter outputPrinter;
    public CommandExecutorFactory(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        this.parkingLotService = parkingLotService;
        this.outputPrinter = outputPrinter;
    }
    public CommandExecutor getCommandExecutor(Command command) throws InvalidCommandException {
        CommandExecutor commandExecutor = null;
        switch (command.getName()) {
            case ParkCommandExecutor.COMMAND_NAME:
                commandExecutor = new ParkCommandExecutor(parkingLotService, outputPrinter);
                break;
            case CreateParkingLotCommandExecutor.COMMAND_NAME:
                commandExecutor = new CreateParkingLotCommandExecutor(parkingLotService, outputPrinter);
                break;
            case ExitCommandExecutor.COMMAND_NAME:
                commandExecutor = new ExitCommandExecutor(parkingLotService, outputPrinter);
                break;
            case LeaveCommandExecutor.COMMAND_NAME:
                commandExecutor = new LeaveCommandExecutor(parkingLotService, outputPrinter);
                break;
            case StatusCommandExecutor.COMMAND_NAME:
                commandExecutor = new StatusCommandExecutor(parkingLotService, outputPrinter);
                break;
            default:
                throw new InvalidCommandException("Invalid command: " + command.getName() + " " + command.getParams());
        }
        return commandExecutor;
    }
}
