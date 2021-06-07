import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private static String createAccount(Scanner scanner) {
        System.out.println("First name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last name: ");
        String lastName = scanner.nextLine();
        String email = null;
        boolean validEmail = false;
        while (!validEmail) {
            try {
                System.out.println("Email (format: name@example.com): ");
                email = scanner.nextLine();
                HotelResource.createCustomer(email, firstName, lastName);
                System.out.println("Account created successfully!\n");
                validEmail = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return email;
    }

    private static void findAndReserveRoom(Scanner scanner) {
        // get valid check-in date input
        Date checkInDate = getValidCheckInDate(scanner);
        // get valid check-out date input
        Date checkOutDate = getValidCheckOutDate(scanner, checkInDate);
        // display available rooms (if any) and ask if wants to book
        Collection<IRoom> availableRooms = HotelResource.findRooms(checkInDate, checkOutDate);
        boolean wantsToBook = false;
        if (availableRooms.isEmpty()) {
            // recommend other dates: expand check-in/out dates by 7 days and check for available rooms again
            Date newCheckInDate = getRecommendedDate(checkInDate);
            Date newCheckOutDate = getRecommendedDate(checkOutDate);
            availableRooms = HotelResource.findRooms(newCheckInDate, newCheckOutDate);
            if (!availableRooms.isEmpty()) {
                System.out.println("There are no available rooms for those dates. Rooms available for alternative dates, check-in on " + newCheckInDate + " and check-out on " + newCheckOutDate);
                wantsToBook = showAvailableRoomsAndAskToBook(scanner, availableRooms);
                checkInDate = newCheckInDate;
                checkOutDate = newCheckOutDate;
            } else {
                System.out.println("There are no available rooms for those dates");
            }
        } else {
            System.out.println("Available rooms for check-in on " + checkInDate + " and check-out on " + checkOutDate);
            wantsToBook = showAvailableRoomsAndAskToBook(scanner, availableRooms);
        }
        if (!wantsToBook) {
            return;
        }
        // ask if user has an account, if yes, ask for their email, else create a new account
        Customer customer = getCustomerForReservation(scanner);
        if (customer == null) {
            System.out.println("Sorry, no account exists for that email");
            return;
        }
        // ask what room would they like to reserve, ask for room number, validate it's available
        IRoom room = getRoomForReservation(scanner, availableRooms);
        // finally, book room and show reservation details
        Reservation reservation = HotelResource.bookRoom(customer.getEmail(), room, checkInDate, checkOutDate);
        System.out.println("Thank you! Your room was booked successfully!");
        System.out.println(reservation);
    }

    private static Date getValidCheckInDate(Scanner scanner) {
        SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
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
        return checkInDate;
    }

    private static Date getValidCheckOutDate(Scanner scanner, Date checkInDate) {
        SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
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
        return checkOutDate;
    }

    private static Date getRecommendedDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 7);
        return c.getTime();
    }

    private static boolean showAvailableRoomsAndAskToBook(Scanner scanner, Collection<IRoom> availableRooms) {
        for (IRoom room : availableRooms) {
            System.out.println(room.toString());
        }
        System.out.println();
        System.out.println("Would you like to book a room? Enter y/yes, or any other character for no:");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    private static Customer getCustomerForReservation(Scanner scanner) {
        String email = null;
        boolean hasAccount = false;
        System.out.println("Do you already have an account with us? Enter y/yes, or any other character for no:");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
            hasAccount = true;
        }
        if (hasAccount) {
            System.out.println("Please enter your Email (format: name@example.com): ");
            email = scanner.nextLine();
        } else {
            email = createAccount(scanner);
        }
        return HotelResource.getCustomer(email);
    }

    private static IRoom getRoomForReservation(Scanner scanner, Collection<IRoom> availableRooms) {
        IRoom room = null;
        String roomNumber = null;
        boolean validRoomNumber = false;
        while (!validRoomNumber) {
            System.out.println("What room would you like to reserve? Enter the room number: ");
            roomNumber = scanner.nextLine();
            room = HotelResource.getRoom(roomNumber);
            if (room == null) { // room doesn't exists, ask again
                System.out.println("That room doesn't exists, please enter a valid room number");
            } else { // room exists, validate it's available
                if (!availableRooms.contains(room)) { // room not available, ask again
                    System.out.println("That room is not available, please enter a valid room number");
                } else {
                    validRoomNumber = true;
                }
            }
        }
        return room;
    }

}
