import java.util.Scanner;

public class Console {

    private static final String MOVE_CURSOR_TO_TOP = "\u001B[H";
    private static final String CLEAR_ENTIRE_SCREEN = "\u001B[2J";

    private final Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    public void clearScreen() {
        System.out.print(MOVE_CURSOR_TO_TOP + CLEAR_ENTIRE_SCREEN);
        System.out.flush();
    }

    public String readText(String message) {
        System.out.print(message);
        return readLine();
    }

    public int readNumber(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(readLine().trim());
            } catch (NumberFormatException wrongInput) {
                System.out.println(
                        "Fehler: Bitte eine ganze Zahl eingeben, zum Beispiel 10."
                );
            }
        }
    }

    public int readChoice(String message, int lowestChoice, int highestChoice) {
        while (true) {
            int choice = readNumber(message);

            if (choice >= lowestChoice && choice <= highestChoice) {
                return choice;
            }

            System.out.println(
                    "Fehler: Bitte eine Zahl von "
                            + lowestChoice
                            + " bis "
                            + highestChoice
                            + " eingeben."
            );
        }
    }

    public int readPositiveNumber(String message) {
        while (true) {
            int number = readNumber(message);

            if (number > 0) {
                return number;
            }

            System.out.println(
                    "Fehler: Bitte eine Zahl grösser als 0 eingeben."
            );
        }
    }

    public void close() {
        scanner.close();
    }

    private String readLine() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nKeine Eingabe mehr möglich.");
            System.exit(0);
        }

        return scanner.nextLine();
    }
}
