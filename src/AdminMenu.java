import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
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
                getAllCustomers();
                break;
            case 2:
                getAllRooms();
                break;
            case 3:
                System.out.println("Admin 3");
                break;
            case 4:
                addRooms(scanner);
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

    private static void getAllCustomers() {
        Collection<Customer> allCustomers = AdminResource.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println("There are no customers");
        } else {
            for (Customer customer : allCustomers) {
                System.out.println(customer.toString());
            }
        }
        System.out.println();
    }

    private static void addRooms(Scanner scanner) {
        boolean keepAddingRooms;
        do {
            addRoom(scanner);
            System.out.println("Would you like to add another room? (y/n): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                keepAddingRooms = true;
            } else {
                keepAddingRooms = false; // for any other input, stop adding rooms
            }
        } while (keepAddingRooms);
    }

    private static void addRoom(Scanner scanner) {
        // get room number input
        System.out.println("Enter room number: ");
        String roomNumber = scanner.nextLine();
        // get valid price input
        double price = 0.00;
        boolean validPrice = false;
        while (!validPrice) {
            try {
                System.out.println("Enter price per night: ");
                price = Double.parseDouble(scanner.nextLine());
                if (price < 0) {
                    System.out.println("The price must be greater or equal than 0.00");
                } else {
                    validPrice = true;
                }
            } catch (Exception ex) {
                System.out.println("Please enter a valid price");
            }
        }
        // get valid room type input
        RoomType roomType = null;
        boolean validRoomType = false;
        while (!validRoomType) {
            try {
                System.out.println("Enter room type (1 for single bed, 2 for double bed): ");
                roomType = RoomType.valueforNumberOfBeds(Integer.parseInt(scanner.nextLine()));
                if (roomType == null) {
                    System.out.println("Please enter a valid room type");
                } else {
                    validRoomType = true;
                }
            } catch (Exception ex) {
                System.out.println("Please enter a valid room type");
            }
        }
        // create and add the room
        Room newRoom = new Room(roomNumber, price, roomType);
        AdminResource.addRoom(newRoom);
    }

    private static void getAllRooms() {
        Collection<IRoom> allRooms = AdminResource.getAllRooms();
        if (allRooms.isEmpty()) {
            System.out.println("There are no rooms");
        } else {
            for (IRoom room : allRooms) {
                System.out.println(room.toString());
            }
        }
        System.out.println();
    }

}
