package commands;

import models.Command;
import services.ParkingLotService;
import utils.IntegerValidator;
import utils.OutputPrinter;
import java.util.List;

public class CreateParkingLotCommandExecutor extends CommandExecutor {
    public static final String COMMAND_NAME = "create_parking_lot";
    public CreateParkingLotCommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public void execute(Command command) {
        Integer capacity = Integer.parseInt(command.getParams().get(0));
        parkingLotService.createParkingLot(capacity);
        outputPrinter.printWithNewLine("Parking lot created with capacity " + capacity);
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
