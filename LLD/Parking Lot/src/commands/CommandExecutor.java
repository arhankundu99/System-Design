package commands;

import models.Command;
import services.ParkingLotService;
import utils.OutputPrinter;

public abstract class CommandExecutor {
    ParkingLotService parkingLotService;
    OutputPrinter outputPrinter;
    CommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        this.parkingLotService = parkingLotService;
        this.outputPrinter = outputPrinter;
    }
    public abstract void execute(Command command);
    public abstract boolean isValid(Command command);
}
