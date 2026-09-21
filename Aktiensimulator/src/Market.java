import java.util.LinkedHashMap;
import java.util.Map;

public class Market {
    private Map<String, Stock> stocks;
    private PriceChangeStrategy priceChangeStrategy;

    public Market(PriceChangeStrategy priceChangeStrategy) {
        this.stocks = new LinkedHashMap<>();
        this.priceChangeStrategy = priceChangeStrategy;
    }

    public void addStock(Stock stock) {
        stocks.put(stock.getTicker(), stock);
    }

    public Stock getStock(String ticker) {
        return stocks.get(ticker.toUpperCase());
    }

    public boolean stockExists(String ticker) {
        return stocks.containsKey(ticker.toUpperCase());
    }

    public void showStocks() {
        System.out.println("\n================ AKTIENMARKT ================");

        System.out.printf(
                "%-4s | %-15s | %-12s%n",
                "ID",
                "Aktie",
                "Kurs"
        );

        System.out.println("----------------------------------------------");

        for (Stock stock : stocks.values()) {
            System.out.println(stock);
        }

        System.out.println("----------------------------------------------");
    }

    public void nextDay() {
        for (Stock stock : stocks.values()) {
            double oldPrice = stock.getPrice();
            double newPrice =
                    priceChangeStrategy.calculateNewPrice(oldPrice);

            stock.setPrice(newPrice);
        }
    }

    public void showChart(String ticker) {
        Stock stock = getStock(ticker);

        if (stock == null) {
            System.out.println("Diese Aktie existiert nicht.");
            return;
        }

        System.out.println("\n========== KURSGRAFIK ==========");
        System.out.println(stock.getName() + " (" + stock.getTicker() + ")");
        System.out.println("Aktueller Kurs: "
                + String.format("%.2f CHF", stock.getPrice()));

        printAsciiChart(stock);

        System.out.println("Tag 1 ------------------> Heute");
    }

    private void printAsciiChart(Stock stock) {
        int height = 10;

        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;

        for (double price : stock.getPriceHistory()) {
            if (price < minPrice) {
                minPrice = price;
            }

            if (price > maxPrice) {
                maxPrice = price;
            }
        }

        double difference = maxPrice - minPrice;

        if (difference == 0) {
            difference = 1;
        }

        for (int row = height; row >= 0; row--) {
            String line = "";

            for (double price : stock.getPriceHistory()) {
                int chartPosition = (int) Math.round(
                        ((price - minPrice) / difference) * height
                );

                if (chartPosition == row) {
                    line += "* ";
                } else {
                    line += "  ";
                }
            }

            System.out.println(line);
        }
    }
}