package commands;

import exceptions.NoFreeSlotAvailableException;
import exceptions.SlotAlreadyFullException;
import models.Car;
import models.Command;
import services.ParkingLotService;
import utils.OutputPrinter;

public class ParkCommandExecutor extends CommandExecutor {
    public static final String COMMAND_NAME = "park";
    public ParkCommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public void execute(Command command) {
        Car car = new Car(command.getParams().get(0), command.getParams().get(1));
        try {
            parkingLotService.parkCar(car);
        } catch (NoFreeSlotAvailableException e) {
            outputPrinter.printWithNewLine(e.getMessage());
        } catch (SlotAlreadyFullException e) {
            outputPrinter.printWithNewLine(e.getMessage());
        }
    }

    @Override
    public boolean isValid(Command command) {
        return command.getParams().size() == 2;
    }
}
