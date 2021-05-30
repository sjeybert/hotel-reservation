package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservationService {

    Collection<Reservation> reservations;

    private static Map<String, IRoom> roomMap = new HashMap<String, IRoom>();

    public static void addRoom(IRoom room) {
        roomMap.put(room.getRoomNumber(), room);
    }

    public static IRoom getRoom(String roomNumber) {
        return roomMap.get(roomNumber);
    }

    public static Reservation reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        return null;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return null;
    }

    public static Collection<Reservation> getCustomerReservations(Customer customer) {
        return null;
    }

    public static void printAllReservations() {

    }

    public static Collection<IRoom> getAllRooms() {
        return roomMap.values();
    }

}
