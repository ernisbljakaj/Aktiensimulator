import java.util.Random;

public class RandomPriceChangeStrategy implements PriceChangeStrategy {
    private Random random;
    private double maximumChange;

    public RandomPriceChangeStrategy(double maximumChange) {
        this.random = new Random();
        this.maximumChange = maximumChange;
    }

    @Override
    public double calculateNewPrice(double oldPrice) {
        double change = (random.nextDouble() * 2 - 1) * maximumChange;
        double newPrice = oldPrice * (1 + change);

        if (newPrice < 1.00) {
            newPrice = 1.00;
        }

        return newPrice;
    }
}