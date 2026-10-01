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

            stock.updatePrice(newPrice);
        }
    }

    public void showChart(String ticker) {
        Stock stock = getStock(ticker);

        if (stock == null) {
            System.out.println("Diese Aktie existiert nicht.");
            return;
        }

        PriceChart chart = new PriceChart(stock.getPriceHistory());

        System.out.println("\n============== KURSGRAFIK ==============");
        System.out.println(stock.getName() + " (" + stock.getTicker() + ")");
        System.out.printf("Aktueller Kurs: %.2f CHF%n", stock.getPrice());
        System.out.printf(
                "Hoch: %.2f CHF | Tief: %.2f CHF | Veränderung seit Tag 1: %+.2f CHF (%+.2f %%)%n",
                chart.getHighestPrice(),
                chart.getLowestPrice(),
                chart.getChange(),
                chart.getChangePercent()
        );

        chart.printChart();
    }
}