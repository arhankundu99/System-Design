package commands;

import models.Command;
import models.Slot;
import services.ParkingLotService;
import utils.OutputPrinter;
import java.util.List;

public class StatusCommandExecutor extends CommandExecutor {
    public static final String COMMAND_NAME = "status";
    public StatusCommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public void execute(Command command) {
        List<Slot> unavailableSlots = parkingLotService.getUnavailableSlots();
        for (Slot slot : unavailableSlots) {
            outputPrinter.printWithNewLine(slot.toString());
        }
    }

    @Override
    public boolean isValid(Command command) {
        final List<String> params = command.getParams();
        return params.size() == 0;
    }
}
