import java.util.Scanner;

public class HotelApplication {

    public static void main(String[] args) {
        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepRunning) {
                try {
                    MainMenu.displayOptions();
                    int selection = Integer.parseInt(scanner.nextLine());
                    keepRunning = MainMenu.executeOption(scanner, selection);
                } catch (Exception ex) {
                    System.out.println("Please enter a number between 1 and 5\n");
                }
            }
        } catch (Exception ex) {
            System.out.println("\nError - Exiting program...\n");
        }
    }

}
