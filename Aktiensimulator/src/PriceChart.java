import java.util.Arrays;
import java.util.List;

public class PriceChart {

    private static final int CHART_HEIGHT = 12;
    private static final int CHART_WIDTH = 60;
    private static final int LABEL_WIDTH = 8;
    private static final char EMPTY = ' ';
    private static final char POINT = '*';

    private final List<Double> prices;

    public PriceChart(List<Double> prices) {
        this.prices = prices;
    }

    public void printChart() {
        if (prices.size() < 2) {
            System.out.println("Für diese Aktie gibt es noch keinen Kursverlauf.");
            return;
        }

        double lowestPrice = getLowestPrice();
        double highestPrice = getHighestPrice();
        char[][] plot = buildPlot(lowestPrice, highestPrice);

        for (int row = 0; row < CHART_HEIGHT; row++) {
            System.out.println(
                    getPriceLabel(row, lowestPrice, highestPrice) + " |" + asText(plot[row])
            );
        }

        printDayAxis();
    }

    public double getHighestPrice() {
        double highestPrice = prices.get(0);

        for (double price : prices) {
            if (price > highestPrice) {
                highestPrice = price;
            }
        }

        return highestPrice;
    }

    public double getLowestPrice() {
        double lowestPrice = prices.get(0);

        for (double price : prices) {
            if (price < lowestPrice) {
                lowestPrice = price;
            }
        }

        return lowestPrice;
    }

    public double getChange() {
        return prices.get(prices.size() - 1) - prices.get(0);
    }

    public double getChangePercent() {
        double firstPrice = prices.get(0);

        if (firstPrice == 0.0) {
            return 0.0;
        }

        return getChange() / firstPrice * 100;
    }

    private char[][] buildPlot(double lowestPrice, double highestPrice) {
        char[][] plot = new char[CHART_HEIGHT][CHART_WIDTH];

        for (char[] row : plot) {
            Arrays.fill(row, EMPTY);
        }

        for (int index = 0; index < prices.size(); index++) {
            plot[getRow(index, lowestPrice, highestPrice)][getColumn(index)] = POINT;
        }

        for (int index = 1; index < prices.size(); index++) {
            drawLine(plot, index - 1, index, lowestPrice, highestPrice);
        }

        return plot;
    }

    private void drawLine(
            char[][] plot,
            int fromIndex,
            int toIndex,
            double lowestPrice,
            double highestPrice
    ) {
        int fromColumn = getColumn(fromIndex);
        int toColumn = getColumn(toIndex);
        int fromRow = getRow(fromIndex, lowestPrice, highestPrice);
        int toRow = getRow(toIndex, lowestPrice, highestPrice);

        int steps = Math.max(Math.abs(toColumn - fromColumn), Math.abs(toRow - fromRow));
        char line = getLineSymbol(toColumn - fromColumn, toRow - fromRow);

        for (int step = 1; step < steps; step++) {
            int column = fromColumn + (toColumn - fromColumn) * step / steps;
            int row = fromRow + (toRow - fromRow) * step / steps;

            if (plot[row][column] == EMPTY) {
                plot[row][column] = line;
            }
        }
    }

    private int getColumn(int index) {
        if (prices.size() == 1) {
            return 0;
        }

        return (int) Math.round(
                index * (double) (CHART_WIDTH - 1) / (prices.size() - 1)
        );
    }

    private int getRow(int index, double lowestPrice, double highestPrice) {
        double span = highestPrice - lowestPrice;

        if (span == 0.0) {
            return CHART_HEIGHT / 2;
        }

        return (int) Math.round(
                (highestPrice - prices.get(index)) / span * (CHART_HEIGHT - 1)
        );
    }

    private char getLineSymbol(int columnChange, int rowChange) {
        if (columnChange == 0) {
            return '|';
        }

        if (rowChange == 0) {
            return '-';
        }

        return rowChange < 0 ? '/' : '\\';
    }

    private String getPriceLabel(int row, double lowestPrice, double highestPrice) {
        if (row == 0) {
            return formatPrice(highestPrice);
        }

        if (row == CHART_HEIGHT - 1) {
            return formatPrice(lowestPrice);
        }

        if (row == CHART_HEIGHT / 2) {
            return formatPrice((highestPrice + lowestPrice) / 2);
        }

        return " ".repeat(LABEL_WIDTH);
    }

    private String formatPrice(double price) {
        return String.format("%" + LABEL_WIDTH + ".2f", price);
    }

    private String asText(char[] row) {
        int end = row.length;

        while (end > 0 && row[end - 1] == EMPTY) {
            end--;
        }

        return new String(row, 0, end);
    }

    private void printDayAxis() {
        int indent = LABEL_WIDTH + 2;
        String firstLabel = "Tag 1";
        String lastLabel = "Tag " + prices.size();
        int gap = CHART_WIDTH - lastLabel.length() - firstLabel.length();

        System.out.println(
                " ".repeat(indent) + "+" + "-".repeat(CHART_WIDTH - 1)
        );
        System.out.println(
                " ".repeat(indent)
                        + firstLabel
                        + " ".repeat(Math.max(gap, 1))
                        + lastLabel
        );
    }
}
