public class Main {

    private static final double MAXIMUM_PRICE_CHANGE = 0.10;
    private static final double STARTING_CAPITAL = 10000.00;
    private static final int LOWEST_MENU_CHOICE = 1;
    private static final int HIGHEST_MENU_CHOICE = 7;

    private static final Console console = new Console();

    public static void main(String[] args) {
        Market market = createMarket();
        Investor investor = new Investor(STARTING_CAPITAL);

        int day = 1;
        boolean running = true;

        while (running) {
            console.clearScreen();
            showTitle();
            showMenu(day, investor);

            int choice = console.readChoice(
                    "Auswahl: ",
                    LOWEST_MENU_CHOICE,
                    HIGHEST_MENU_CHOICE
            );

            switch (choice) {
                case 1:
                    market.showStocks();
                    break;

                case 2:
                    buyStock(market, investor);
                    break;

                case 3:
                    sellStock(market, investor);
                    break;

                case 4:
                    investor.getPortfolio().showPortfolio();
                    break;

                case 5:
                    showChart(market);
                    break;

                case 6:
                    market.nextDay();
                    day++;

                    System.out.println("\nEin neuer Tag beginnt.");
                    System.out.println("Alle Aktienkurse wurden verändert.");
                    break;

                case 7:
                    showEndResult(investor, day);
                    running = false;
                    break;
            }
        }

        console.close();
    }

    private static Market createMarket() {
        PriceChangeStrategy strategy =
                new RandomPriceChangeStrategy(MAXIMUM_PRICE_CHANGE);

        Market market = new Market(strategy);

        market.addStock(new Stock("SwissTech", "STC", 120.00));
        market.addStock(new Stock("GreenEnergy", "GRE", 80.00));
        market.addStock(new Stock("FoodWorld", "FOO", 50.00));
        market.addStock(new Stock("AutoFuture", "ATF", 200.00));

        return market;
    }

    private static void showTitle() {
        System.out.println("========================================");
        System.out.println("       AKTIENMARKT-SIMULATOR");
        System.out.println("========================================");
        System.out.printf("Startkapital: %.2f CHF%n", STARTING_CAPITAL);
    }

    private static void showMenu(int day, Investor investor) {
        System.out.println("\n========================================");
        System.out.println("Tag: " + day);
        System.out.printf("Bargeld: %.2f CHF%n", investor.getCash());
        System.out.printf(
                "Gesamtvermögen: %.2f CHF%n",
                investor.getTotalWealth()
        );
        System.out.println("----------------------------------------");
        System.out.println("1. Aktienmarkt anzeigen");
        System.out.println("2. Aktie kaufen");
        System.out.println("3. Aktie verkaufen");
        System.out.println("4. Portfolio anzeigen");
        System.out.println("5. Kursgrafik anzeigen");
        System.out.println("6. Nächster Tag");
        System.out.println("7. Spiel beenden");
        System.out.println("========================================");
    }

    private static void buyStock(Market market, Investor investor) {
        market.showStocks();

        String ticker = console.readText(
                "Ticker der Aktie zum Kaufen eingeben (z. B. STC): "
        ).toUpperCase();

        Stock stock = market.getStock(ticker);

        if (stock == null) {
            System.out.println("Fehler: Dieser Ticker existiert nicht.");
            return;
        }

        int quantity = console.readPositiveNumber(
                "Wie viele Stück möchtest du kaufen? "
        );

        boolean success = investor.buy(stock, quantity);

        if (success) {
            double totalCost = stock.getPrice() * quantity;

            System.out.println("\nKauf erfolgreich:");
            System.out.println(quantity + " Stück von "
                    + stock.getTicker() + " gekauft.");
            System.out.printf("Bezahlt: %.2f CHF%n", totalCost);
        } else {
            System.out.println(
                    "Kauf nicht möglich: Zu wenig Geld oder ungültige Anzahl."
            );
        }
    }

    private static void sellStock(Market market, Investor investor) {
        market.showStocks();

        String ticker = console.readText(
                "Ticker der Aktie zum Verkaufen eingeben (z. B. STC): "
        ).toUpperCase();

        Stock stock = market.getStock(ticker);

        if (stock == null) {
            System.out.println("Fehler: Dieser Ticker existiert nicht.");
            return;
        }

        int quantity = console.readPositiveNumber(
                "Wie viele Stück möchtest du verkaufen? "
        );

        boolean success = investor.sell(stock, quantity);

        if (success) {
            double totalRevenue = stock.getPrice() * quantity;

            System.out.println("\nVerkauf erfolgreich:");
            System.out.println(quantity + " Stück von "
                    + stock.getTicker() + " verkauft.");
            System.out.printf("Erhalten: %.2f CHF%n", totalRevenue);
        } else {
            System.out.println(
                    "Verkauf nicht möglich: Du besitzt nicht genug Aktien."
            );
        }
    }

    private static void showChart(Market market) {
        market.showStocks();

        String ticker = console.readText(
                "Ticker für die Kursgrafik eingeben (z. B. STC): "
        ).toUpperCase();

        market.showChart(ticker);
    }

    private static void showEndResult(Investor investor, int day) {
        System.out.println("\n========================================");
        System.out.println("             SPIEL BEENDET");
        System.out.println("========================================");
        System.out.println("Gespielte Tage: " + day);
        System.out.printf("Bargeld: %.2f CHF%n", investor.getCash());
        System.out.printf(
                "Wert der Aktien: %.2f CHF%n",
                investor.getPortfolio().getTotalValue()
        );
        System.out.printf(
                "Gesamtvermögen: %.2f CHF%n",
                investor.getTotalWealth()
        );
        System.out.println("========================================");
    }
}
