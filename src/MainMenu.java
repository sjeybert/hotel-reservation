import api.HotelResource;

import java.util.Scanner;

public class MainMenu {

    public static void displayOptions() {
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an Account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("==========================================================");
        System.out.println("Please select a number for the menu option");
    }

    public static boolean executeOption(Scanner scanner, Integer selection) {
        boolean keepRunning = true;
        switch (selection) {
            case 1:
                System.out.println("Main 1");
                break;
            case 2:
                System.out.println("Main 2");
                break;
            case 3:
                createAccount(scanner);
                break;
            case 4:
                runAdminMenu(scanner);
                break;
            case 5:
                keepRunning = false;
                break;
            default:
                System.out.println("Please enter a number between 1 and 5\n");
        }
        return keepRunning;
    }

    private static void runAdminMenu(Scanner scanner) {
        boolean keepAdminRunning = true;
        while (keepAdminRunning) {
            try {
                AdminMenu.displayOptions();
                int adminSelection = Integer.parseInt(scanner.nextLine());
                keepAdminRunning = AdminMenu.executeOption(scanner, adminSelection);
            } catch (Exception ex) {
                System.out.println("Please enter a number between 1 and 6\n");
            }
        }
    }

    private static void createAccount(Scanner scanner) {
        System.out.println("First name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last name: ");
        String lastName = scanner.nextLine();
        boolean validEmail = false;
        while (!validEmail) {
            try {
                System.out.println("Email (format: name@example.com): ");
                String email = scanner.nextLine();
                HotelResource.createCustomer(email, firstName, lastName);
                System.out.println("Account created successfully!\n");
                validEmail = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

}
