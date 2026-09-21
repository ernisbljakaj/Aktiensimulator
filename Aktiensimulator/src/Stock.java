import java.util.ArrayList;
import java.util.List;

public class Stock {
    private String name;
    private String ticker;
    private double price;
    private List<Double> priceHistory;

    public Stock(String name, String ticker, double price) {
        this.name = name;
        this.ticker = ticker;
        this.price = price;
        this.priceHistory = new ArrayList<>();

        priceHistory.add(price);
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public List<Double> getPriceHistory() {
        return priceHistory;
    }

    public void setPrice(double price) {
        this.price = price;
        priceHistory.add(price);
    }

    @Override
    public String toString() {
        return String.format(
                "%-4s | %-15s | %8.2f CHF",
                ticker,
                name,
                price
        );
    }
}