import exceptions.InvalidCommandException;
import exceptions.InvalidModeException;
import mode.FileMode;
import mode.InteractiveMode;
import mode.Mode;
import services.ParkingLotService;
import strategies.ISlotAssignmentStrategy;
import strategies.SlotAssignmentStrategy;
import utils.OutputPrinter;

public class Main {
    public static void main(String[] args) throws InvalidCommandException, InvalidModeException {
        int MAX_CAPACITY = 1000;
        ISlotAssignmentStrategy slotAssignmentStrategy = new SlotAssignmentStrategy(MAX_CAPACITY);
        ParkingLotService parkingLotService = new ParkingLotService(MAX_CAPACITY, slotAssignmentStrategy);
        OutputPrinter outputPrinter = new OutputPrinter();

        Mode mode;
        if (args.length == 0) {
            mode = new InteractiveMode(parkingLotService, outputPrinter);
            mode.process();
        } else if (args.length == 1) {
            mode = new FileMode(parkingLotService, outputPrinter, args[0]);
            mode.process();
        } else {
            throw new InvalidModeException("Invalid mode");
        }

    }
}
