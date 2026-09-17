import java.util.List;
import java.util.ArrayList;

public class Market {
    private List<Stock> stocks;
    
    public Market() {
     stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void showStocks() {
        System.out.println("\n--- Aktienmarkt ---");

        for (int i = 0; i < stocks.size(); i++) {
            System.out.println((i + 1) + ". " + stocks.get(i));
        }
    }
}
