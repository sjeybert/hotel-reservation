package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Map;

public class AdminResource {

    public static Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    public static void addRoom(IRoom room) {
        ReservationService.addRoom(room);
    }

    public static Map<String, IRoom> getAllRooms() {
        return ReservationService.getAllRooms();
    }

    public static Map<String, Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    public static void displayAllReservations() {
        ReservationService.printAllReservations();
    }
}
