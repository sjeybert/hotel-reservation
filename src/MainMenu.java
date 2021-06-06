import api.HotelResource;
import model.Customer;
import model.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
                findAndReserveRoom(scanner);
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

    private static void findAndReserveRoom(Scanner scanner) {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        // get valid check-in date input
        Date checkInDate = null;
        boolean validCheckInDate = false;
        while (!validCheckInDate) {
            System.out.println("Check-in date (mm/dd/yyyy): ");
            String inputCheckInDate = scanner.nextLine();
            try {
                checkInDate = DateFor.parse(inputCheckInDate);
                Date today = new Date();
                if (checkInDate.before(today)) { // check-in date can't be in the past
                    System.out.println("The check-in date cannot be in the past");
                } else {
                    validCheckInDate = true;
                }
            } catch (ParseException ex) {
                System.out.println("Invalid date format, please use dd/mm/yyyy");
            }
        }
        // get valid check-out date input
        Date checkOutDate = null;
        boolean validCheckOutDate = false;
        while (!validCheckOutDate) {
            System.out.println("Check-out date (mm/dd/yyyy): ");
            String inputCheckOutDate = scanner.nextLine();
            try {
                checkOutDate = DateFor.parse(inputCheckOutDate);
                if (checkOutDate.before(checkInDate)) { // check-out date can't be before the check-in date
                    System.out.println("The check-out date can't be before the check-in date");
                } else {
                    validCheckOutDate = true;
                }
            } catch (ParseException ex) {
                System.out.println("Invalid date format, please use dd/mm/yyyy");
            }
        }

        // display available rooms
        Collection<IRoom> availableRooms = HotelResource.findRooms(checkInDate, checkOutDate);
        if (availableRooms.isEmpty()) {
            System.out.println("There are no available rooms for those dates");
        } else {
            System.out.println("Available rooms for check-in on " + checkInDate + " and check-out on " + checkOutDate);
            for (IRoom room : availableRooms) {
                System.out.println(room.toString());
            }
        }
        System.out.println();

        // ask if would like to book a room

        // ask if have an account, if yes ask for email, if no create new

        // ask what room would you like to reserve, ask for room number

        // show reservation details and go back to main menu
    }

}
