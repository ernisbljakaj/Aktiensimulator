public class Main {

    private static final double MAXIMUM_PRICE_CHANGE = 0.10;
    private static final double STARTING_CAPITAL = 10000.00;
    private static final int LOWEST_MENU_CHOICE = 1;
    private static final int HIGHEST_MENU_CHOICE = 7;

    private static final int DAYS_PER_LEVEL = 30;
    private static final double LEVEL_GOAL_STEP = 1000.00;

    private static final Console console = new Console();

    public static void main(String[] args) {
        Market market = createMarket();
        Investor investor = new Investor(STARTING_CAPITAL);

        int level = 1;
        int day = 1;
        double goal = goalForLevel(level);
        boolean running = true;

        while (running) {
            console.clearScreen();
            showTitle();
            showMenu(day, level, goal, investor);

            int choice = console.readChoice(
                    "Auswahl: ",
                    LOWEST_MENU_CHOICE,
                    HIGHEST_MENU_CHOICE);

            console.clearScreen();

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

                    if (day > DAYS_PER_LEVEL) {
                        running = finishLevel(investor, level, goal);

                        if (running) {
                            level++;
                            day = 1;
                            goal = goalForLevel(level);
                        }
                    } else {
                        System.out.println("\nEin neuer Tag beginnt.");
                        System.out.println("Alle Aktienkurse wurden verändert.");
                    }
                    break;

                case 7:
                    showEndResult(investor, level, day);
                    running = false;
                    break;
            }

            if (running) {
                console.waitForEnter(
                        "\nDrücke Enter, um den Bildschirm zu leeren "
                                + "und zum Menü zurückzukehren...");
            }
        }

        console.close();
    }

    private static Market createMarket() {
        PriceChangeStrategy strategy = new RandomPriceChangeStrategy(MAXIMUM_PRICE_CHANGE);

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

    private static void showMenu(int day, int level, double goal, Investor investor) {
        double wealth = investor.getTotalWealth();

        System.out.println("\n========================================");
        System.out.printf(
                "Level: %d | Tag: %d von %d%n",
                level,
                day,
                DAYS_PER_LEVEL);
        System.out.printf("Ziel dieses Levels: %.2f CHF%n", goal);
        System.out.println("----------------------------------------");
        System.out.printf("Bargeld: %.2f CHF%n", investor.getCash());
        System.out.printf("Gesamtvermögen: %.2f CHF%n", wealth);

        if (wealth >= goal) {
            System.out.println("Ziel erreicht! Halte durch bis Tag "
                    + DAYS_PER_LEVEL + ".");
        } else {
            System.out.printf("Noch nötig: %.2f CHF%n", goal - wealth);
        }

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

    private static double goalForLevel(int level) {
        return STARTING_CAPITAL + (level * LEVEL_GOAL_STEP);
    }

    private static boolean finishLevel(Investor investor, int level, double goal) {
        double wealth = investor.getTotalWealth();

        System.out.println("\n========================================");

        if (wealth >= goal) {
            System.out.printf("     LEVEL %d GESCHAFFT!%n", level);
            System.out.println("========================================");
            System.out.printf("Ziel von %.2f CHF erreicht.%n", goal);
            System.out.printf("Gesamtvermögen: %.2f CHF%n", wealth);
            System.out.printf(
                    "Neues Ziel für Level %d: %.2f CHF%n",
                    level + 1,
                    goalForLevel(level + 1));
            System.out.println("========================================");

            return true;
        }

        System.out.println("              VERLOREN");
        System.out.println("========================================");
        System.out.printf(
                "Du hast Level %d nach %d Tagen nicht geschafft.%n",
                level,
                DAYS_PER_LEVEL);
        System.out.printf("Ziel:     %.2f CHF%n", goal);
        System.out.printf("Erreicht: %.2f CHF%n", wealth);
        System.out.printf("Fehlend:  %.2f CHF%n", goal - wealth);
        System.out.println("========================================");

        return false;
    }

    private static void buyStock(Market market, Investor investor) {
        market.showStocks();

        String ticker = console.readText(
                "Ticker der Aktie zum Kaufen eingeben (z. B. STC): ").toUpperCase();

        Stock stock = market.getStock(ticker);

        if (stock == null) {
            System.out.println("Fehler: Dieser Ticker existiert nicht.");
            return;
        }

        int quantity = console.readPositiveNumber(
                "Wie viele Stück möchtest du kaufen? ");

        double pricePerShare = stock.getPrice();
        double totalCost = pricePerShare * quantity;
        double cashAfterBuy = investor.getCash() - totalCost;

        System.out.println("\n========================================");
        System.out.println("           KAUF BESTÄTIGEN");
        System.out.println("========================================");
        System.out.println(
                "Aktie: " + stock.getName() + " (" + stock.getTicker() + ")");
        System.out.printf("Kurs pro Stück: %.2f CHF%n", pricePerShare);
        System.out.printf("Anzahl:         %d Stück%n", quantity);
        System.out.printf("Gesamtkosten:   %.2f CHF%n", totalCost);
        System.out.printf("Bargeld danach: %.2f CHF%n", cashAfterBuy);

        if (cashAfterBuy < 0) {
            System.out.println(
                    "Warnung: Du hast nicht genug Bargeld für diesen Kauf!");
        }

        System.out.println("========================================");

        if (!console.readConfirmation("Kauf durchführen? (j/n): ")) {
            System.out.println("\nKauf abgebrochen.");
            return;
        }

        boolean success = investor.buy(stock, quantity);

        if (success) {
            System.out.println("\nKauf erfolgreich:");
            System.out.println(quantity + " Stück von "
                    + stock.getTicker() + " gekauft.");
            System.out.printf("Bezahlt: %.2f CHF%n", totalCost);
        } else {
            System.out.println(
                    "Kauf nicht möglich: Zu wenig Geld oder ungültige Anzahl.");
        }
    }

    private static void sellStock(Market market, Investor investor) {
        market.showStocks();

        String ticker = console.readText(
                "Ticker der Aktie zum Verkaufen eingeben (z. B. STC): ").toUpperCase();

        Stock stock = market.getStock(ticker);

        if (stock == null) {
            System.out.println("Fehler: Dieser Ticker existiert nicht.");
            return;
        }

        int quantity = console.readPositiveNumber(
                "Wie viele Stück möchtest du verkaufen? ");

        boolean success = investor.sell(stock, quantity);

        if (success) {
            double totalRevenue = stock.getPrice() * quantity;

            System.out.println("\nVerkauf erfolgreich:");
            System.out.println(quantity + " Stück von "
                    + stock.getTicker() + " verkauft.");
            System.out.printf("Erhalten: %.2f CHF%n", totalRevenue);
        } else {
            System.out.println(
                    "Verkauf nicht möglich: Du besitzt nicht genug Aktien.");
        }
    }

    private static void showChart(Market market) {
        market.showStocks();

        String ticker = console.readText(
                "Ticker für die Kursgrafik eingeben (z. B. STC): ").toUpperCase();

        market.showChart(ticker);
    }

    private static void showEndResult(Investor investor, int level, int day) {
        System.out.println("\n========================================");
        System.out.println("             SPIEL BEENDET");
        System.out.println("========================================");
        System.out.printf("Beendet in Level %d am Tag %d von %d%n",
                level,
                day,
                DAYS_PER_LEVEL);
        System.out.printf("Bargeld: %.2f CHF%n", investor.getCash());
        System.out.printf(
                "Wert der Aktien: %.2f CHF%n",
                investor.getPortfolio().getTotalValue());
        System.out.printf(
                "Gesamtvermögen: %.2f CHF%n",
                investor.getTotalWealth());
        System.out.println("========================================");
    }
}
