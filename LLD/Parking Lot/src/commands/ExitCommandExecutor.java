package commands;

import models.Command;
import utils.OutputPrinter;
import services.ParkingLotService;

public class ExitCommandExecutor extends CommandExecutor {
    public static final String COMMAND_NAME = "exit";

    public ExitCommandExecutor(
            ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final Command command) {
        return command.getParams().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final Command command) {

    }
}