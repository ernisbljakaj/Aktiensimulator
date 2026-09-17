public class Main {
    public static void main(String[] args) {
        Market market = new Market();

        market.addStock(new Stock("SwissTech", "STC", 120.00));
        market.addStock(new Stock("GreenEnergy", "GRE", 80.00));
        market.addStock(new Stock("FoodWorld", "FOO", 50.00));

        market.showStocks();
    }
}