package commands;

import models.Command;
import services.ParkingLotService;
import utils.IntegerValidator;
import utils.OutputPrinter;
import java.util.List;

public class LeaveCommandExecutor extends CommandExecutor {
    public static final String COMMAND_NAME = "leave";
    public LeaveCommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);

    }

    @Override
    public void execute(Command command) {
        Integer slotNumber = Integer.parseInt(command.getParams().get(0));
        parkingLotService.unparkCar(slotNumber);
        outputPrinter.printWithNewLine("Slot " + slotNumber + " has been unparked.");
    }

    @Override
    public boolean isValid(Command command) {
        final List<String> params = command.getParams();
        if (params.size() != 1) {
            return false;
        }
        return IntegerValidator.isInteger(params.get(0));
    }
}
