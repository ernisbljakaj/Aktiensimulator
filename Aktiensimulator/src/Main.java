import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    /**
     *  
     * @param args
     */
    public static void main(String[] args) {
        PriceChangeStrategy strategy =
                new RandomPriceChangeStrategy(0.10);

        Market market = new Market(strategy);

        market.addStock(new Stock("SwissTech", "STC", 120.00));
        market.addStock(new Stock("GreenEnergy", "GRE", 80.00));
        market.addStock(new Stock("FoodWorld", "FOO", 50.00));
        market.addStock(new Stock("AutoFuture", "ATF", 200.00));

        Investor investor = new Investor(10000.00);

        int day = 1;
        boolean running = true;

        System.out.println("========================================");
        System.out.println("       AKTIENMARKT-SIMULATOR");
        System.out.println("========================================");
        System.out.println("Startkapital: 10'000.00 CHF");

        while (running) {
            showMenu(day, investor);

            int choice = readInt("Auswahl: ");

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

                default:
                    System.out.println("Ungültige Auswahl. Bitte 1 bis 7 wählen.");
            }
        }

        scanner.close();
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

        String ticker = readText(
                "Ticker der Aktie zum Kaufen eingeben (z. B. STC): "
        ).toUpperCase();

        Stock stock = market.getStock(ticker);

        if (stock == null) {
            System.out.println("Fehler: Dieser Ticker existiert nicht.");
            return;
        }

        int quantity = readInt("Wie viele Stück möchtest du kaufen? ");

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

        String ticker = readText(
                "Ticker der Aktie zum Verkaufen eingeben (z. B. STC): "
        ).toUpperCase();

        Stock stock = market.getStock(ticker);

        if (stock == null) {
            System.out.println("Fehler: Dieser Ticker existiert nicht.");
            return;
        }

        int quantity = readInt("Wie viele Stück möchtest du verkaufen? ");

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

        String ticker = readText(
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

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                scanner.nextLine();
                return number;
            }

            System.out.println("Fehler: Bitte eine ganze Zahl eingeben.");
            scanner.nextLine();
        }
    }

    private static String readText(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}