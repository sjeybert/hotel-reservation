import java.util.Scanner;

public class AdminMenu {

    public static void displayOptions() {
        System.out.println("Admin Menu");
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Add test data");
        System.out.println("6. Back to main menu");
        System.out.println("==========================================================");
        System.out.println("Please select a number for the menu option");
    }

    public static boolean executeOption(Scanner scanner, Integer selection) {
        boolean keepAdminRunning = true;
        switch (selection) {
            case 1:
                System.out.println("Admin 1");
                break;
            case 2:
                System.out.println("Admin 2");
                break;
            case 3:
                System.out.println("Admin 3");
                break;
            case 4:
                System.out.println("Admin 4");
                break;
            case 5:
                System.out.println("Admin 5");
                break;
            case 6:
                keepAdminRunning = false;
                break;
            default:
                System.out.println("Please enter a number between 1 and 6\n");
        }
        return keepAdminRunning;
    }

}
