package strategies;

public interface ITripPricingStrategy{
    public int getPrice(int origin, int destination, int numSeats);
    public int getPriceForPreferredRiders(int origin, int destination, int numSeats);
}