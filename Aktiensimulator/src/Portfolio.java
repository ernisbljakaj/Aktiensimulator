import java.util.List;
import java.util.ArrayList;

public class Portfolio {
    private List<Position> positions;

    public Portfolio() {
        this.positions = new ArrayList<>();
    }

    public void addStock(Stock stock, int quantity) {
        Position existingPosition = findPosition(stock);

        if (existingPosition == null) {
            positions.add(new Position(stock, quantity));
        } else {
            existingPosition.addQuantity(quantity);
        }

    }

    private Position findPosition(Stock stock) {
        for (Position position : positions) {
            if (position.getStock() == stock) {
                return position;
            }
        }
        return null;
    }

    public void showPortfolio() {
        System.out.println("\n--- Portfolio ---");
        if (positions.isEmpty()) {
            System.out.println("Du besitzt noch keine Aktien.");
            return;
        }

        double totalValue = 0;

        for (Position position : positions) {
            System.out.println(position.getStock().getTicker() + " ¦ Anzahl: " + position.getQuantity() + " ¦ Wert: "
                    + String.format("%.2f CHF", position.getValue()));
            totalValue = totalValue + position.getValue();
        }
        System.out.println("Gesamtwert Aktien: " + String.format("%.2f CHF", totalValue));
    }
}
