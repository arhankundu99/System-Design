package mode;
import exceptions.InvalidCommandException;
import services.ParkingLotService;
import utils.OutputPrinter;
import models.Command;
import commands.CommandExecutorFactory;
import commands.CommandExecutor;

public abstract class Mode {
    private final CommandExecutorFactory commandExecutorFactory;
    protected OutputPrinter outputPrinter;
    protected ParkingLotService parkingLotService;
    public Mode (ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        this.commandExecutorFactory = new CommandExecutorFactory(parkingLotService, outputPrinter);
    }
    public abstract void process() throws InvalidCommandException; // Method to process input
    protected void processCommand(Command command) throws InvalidCommandException {
        CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(command);
        if (commandExecutor != null && commandExecutor.isValid(command)) {
            commandExecutor.execute(command);
        } else {
            throw new InvalidCommandException("The following command is an invalid command: " + command);
        }
    }
}
