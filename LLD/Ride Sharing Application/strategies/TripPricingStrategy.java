package strategies;
import strategies.ITripPricingStrategy;
import exceptions.InvalidNumberOfSeatsException;
public class TripPricingStrategy implements ITripPricingStrategy{
    private final int AMOUNT_PER_KM = 20;
    @Override
    public int getPrice(int origin, int destination, int numSeats){
        if(numSeats <= 0){
            throw new InvalidNumberOfSeatsException("Num Seats is not non zero value. Cannot calculate price.");
        }
        if(numSeats >= 2){
            return Math.abs(destination - origin) * AMOUNT_PER_KM * 0.75 * numSeats;
        }

        return Math.abs(destination - origin) * AMOUNT_PER_KM;

    }
    public int getPriceForPreferredRiders(int origin, int destination, int numSeats){
        if(numSeats <= 0){
            throw new InvalidNumberOfSeatsException("Num Seats is not non zero value. Cannot calculate price.");
        }
        if(numSeats >= 2){
            return Math.abs(destination - origin) * AMOUNT_PER_KM * 0.5 * numSeats;
        }

        return Math.abs(destination - origin) * AMOUNT_PER_KM * 0.75;
    }
}