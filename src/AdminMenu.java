import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
                getAllReservations();
                break;
            case 4:
                addRooms(scanner);
                break;
            case 5:
                addTestData();
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
            System.out.println("Would you like to add another room? Enter y/yes, or any other character for no: ");
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
        String roomNumber = null;
        boolean validRoomNumber = false;
        while (!validRoomNumber) {
            System.out.println("Enter room number: ");
            roomNumber = scanner.nextLine();
            IRoom roomExists = HotelResource.getRoom(roomNumber);
            if (roomExists == null) { // room doesn't exists, continue
                validRoomNumber = true;
            } else { // room exists, either continue and edit it's price and type, or enter a new room number
                System.out.println("That room already exists. Enter y/yes to update it, or any other character to enter another room number: ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                    validRoomNumber = true;
                }
            }
        }
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

    private static void getAllReservations() {
        Collection<Reservation> allReservations = AdminResource.getAllReservations();
        if (allReservations.isEmpty()) {
            System.out.println("There are no reservations");
        } else {
            for (Reservation reservation : allReservations) {
                System.out.println(reservation.toString());
            }
        }
        System.out.println();
    }

    public static void addTestData() {
        // add some rooms
        String roomNumber = null;
        Double price = 0.00;
        RoomType roomType = null;
        for (int i = 1; i <= 10; i++) {
            roomNumber = Integer.toString(i);
            if (i % 2 == 0) {
                price = 200.00;
                roomType = RoomType.valueforNumberOfBeds(2);
            } else {
                price = 100.00;
                roomType = RoomType.valueforNumberOfBeds(1);
            }
            Room newRoom = new Room(roomNumber, price, roomType);
            AdminResource.addRoom(newRoom);
        }
        // add some customer accounts
        HotelResource.createCustomer("test1@mail.com", "Peter", "Parker");
        HotelResource.createCustomer("test2@mail.com", "Clark", "Kent");
        HotelResource.createCustomer("test3@mail.com", "Tony", "Stark");
        HotelResource.createCustomer("test4@mail.com", "Bruce", "Wayne");
        HotelResource.createCustomer("test5@mail.com", "Steve", "Rogers");
        // book some rooms
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        Date checkInDate = null;
        Date checkOutDate = null;
        // reservation 1
        c.setTime(today);
        c.add(Calendar.DATE, 2);
        checkInDate = c.getTime();
        c.setTime(checkInDate);
        c.add(Calendar.DATE, 5);
        checkOutDate = c.getTime();
        HotelResource.bookRoom("test1@mail.com", HotelResource.getRoom("1"), checkInDate, checkOutDate);
        // reservation 2
        c.setTime(today);
        c.add(Calendar.DATE, 4);
        checkInDate = c.getTime();
        c.setTime(checkInDate);
        c.add(Calendar.DATE, 10);
        checkOutDate = c.getTime();
        HotelResource.bookRoom("test3@mail.com", HotelResource.getRoom("4"), checkInDate, checkOutDate);
        // reservation 3
        c.setTime(today);
        c.add(Calendar.DATE, 5);
        checkInDate = c.getTime();
        c.setTime(checkInDate);
        c.add(Calendar.DATE, 3);
        checkOutDate = c.getTime();
        HotelResource.bookRoom("test4@mail.com", HotelResource.getRoom("9"), checkInDate, checkOutDate);

        System.out.println("Test data added!");
    }

}
