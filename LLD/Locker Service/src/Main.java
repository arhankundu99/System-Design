import services.LockerService;
import strategies.RandomOTPGenerationStrategy;
import strategies.RandomSlotAssignmentStrategy;

public class Main {
    public static void main(String[] args) {
        RandomOTPGenerationStrategy randomOTPGenerationStrategy = new RandomOTPGenerationStrategy();
        LockerService service = new LockerService(randomOTPGenerationStrategy);
        RandomSlotAssignmentStrategy randomSlotAssignmentStrategy = new RandomSlotAssignmentStrategy(service);
        service.setSlotAssignmentStrategy(randomSlotAssignmentStrategy);
    }
}
