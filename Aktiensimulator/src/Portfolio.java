import java.util.LinkedHashMap;
import java.util.Map;

public class Portfolio {
    private Map<String, Position> positions;

    public Portfolio() {
        positions = new LinkedHashMap<>();
    }

    public void buyStock(Stock stock, int quantity) {
        String ticker = stock.getTicker();

        if (positions.containsKey(ticker)) {
            Position position = positions.get(ticker);
            position.addQuantity(quantity);
        } else {
            Position newPosition = new Position(stock, quantity);
            positions.put(ticker, newPosition);
        }
    }

    public boolean hasEnoughStocks(String ticker, int quantity) {
        if (!positions.containsKey(ticker)) {
            return false;
        }

        Position position = positions.get(ticker);

        return position.getQuantity() >= quantity;
    }

    public boolean sellStock(String ticker, int quantity) {
        if (!hasEnoughStocks(ticker, quantity)) {
            return false;
        }

        Position position = positions.get(ticker);
        position.removeQuantity(quantity);

        if (position.getQuantity() == 0) {
            positions.remove(ticker);
        }

        return true;
    }

    public double getTotalValue() {
        double totalValue = 0;

        for (Position position : positions.values()) {
            totalValue += position.getValue();
        }

        return totalValue;
    }

    public void showPortfolio() {
        System.out.println("\n============== PORTFOLIO ==============");

        if (positions.isEmpty()) {
            System.out.println("Du besitzt noch keine Aktien.");
            return;
        }

        System.out.printf(
                "%-4s | %-15s | %-7s | %-12s%n",
                "ID",
                "Aktie",
                "Anzahl",
                "Wert"
        );

        System.out.println("----------------------------------------");

        for (Position position : positions.values()) {
            Stock stock = position.getStock();

            System.out.printf(
                    "%-4s | %-15s | %-7d | %8.2f CHF%n",
                    stock.getTicker(),
                    stock.getName(),
                    position.getQuantity(),
                    position.getValue()
            );
        }

        System.out.println("----------------------------------------");
        System.out.printf(
                "Gesamtwert Aktien: %.2f CHF%n",
                getTotalValue()
        );
    }
}