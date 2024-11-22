package mode;

import commands.ExitCommandExecutor;
import exceptions.InvalidCommandException;
import services.ParkingLotService;
import utils.OutputPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import models.Command;

public class FileMode extends Mode {
    private String fileName;
    public FileMode(ParkingLotService parkingLotService, OutputPrinter outputPrinter, String fileName) {
        super(parkingLotService, outputPrinter);
        this.fileName = fileName;
    }

    @Override
    public void process() {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                Command command = new Command(input);
                processCommand(command);

                if (command.getName().equals(ExitCommandExecutor.COMMAND_NAME)) {
                    break;
                }
            }

        } catch (FileNotFoundException | InvalidCommandException e) {
            outputPrinter.printWithNewLine(e.getMessage());
        }
    }
}
