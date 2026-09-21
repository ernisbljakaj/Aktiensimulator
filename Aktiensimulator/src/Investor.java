public class Investor {
    private double cash;
    private Portfolio portfolio;

    public Investor(double startingCash) {
        this.cash = startingCash;
        this.portfolio = new Portfolio();
    }

    public Investor() {
        this(10000.00);
    }

    public double getCash() {
        return cash;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public boolean buy(Stock stock, int quantity) {
        if (quantity <= 0) {
            return false;
        }

        double totalCost = stock.getPrice() * quantity;

        if (cash < totalCost) {
            return false;
        }

        cash -= totalCost;
        portfolio.buyStock(stock, quantity);

        return true;
    }

    public boolean buy(Stock stock) {
        return buy(stock, 1);
    }

    public boolean sell(Stock stock, int quantity) {
        if (quantity <= 0) {
            return false;
        }

        String ticker = stock.getTicker();

        if (!portfolio.hasEnoughStocks(ticker, quantity)) {
            return false;
        }

        double totalRevenue = stock.getPrice() * quantity;

        portfolio.sellStock(ticker, quantity);
        cash += totalRevenue;

        return true;
    }

    public double getTotalWealth() {
        return cash + portfolio.getTotalValue();
    }
}