package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.awt.List;
import java.util.*;
import java.util.function.Supplier;

public class ReservationService {

    private static final Map<String, IRoom> roomMap = new HashMap<String, IRoom>();
    private static final Map<String, Collection<Reservation>> reservationMap = new HashMap<String, Collection<Reservation>>();

    public static void addRoom(IRoom room) {
        roomMap.put(room.getRoomNumber(), room);
    }

    public static IRoom getRoom(String roomNumber) {
        return roomMap.get(roomNumber);
    }

    public static Reservation reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        Collection<Reservation> customerReservations = getCustomerReservations(customer);
        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }
        customerReservations.add(reservation);
        reservationMap.put(customer.getEmail(), customerReservations);
        return reservation;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        // get all rooms reserved within the check-in and check-out dates
        Collection<IRoom> reservedRooms = null;
        for (Reservation reservation : getAllReservations()) {
            if (reservation.isRoomReserved(checkInDate, checkOutDate)) {
                reservedRooms.add(reservation.getRoom());
            }
        }
        // get available rooms (all rooms that are not reserved)
        Collection<IRoom> availableRooms = null;
        for (IRoom room : getAllRooms()) {
            if (!reservedRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public static Collection<Reservation> getCustomerReservations(Customer customer) {
        return reservationMap.get(customer.getEmail());
    }

    public static Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new LinkedList<>();
        for (Collection<Reservation> customerReservations : reservationMap.values()) {
            allReservations.addAll(customerReservations);
        }
        return allReservations;
    }

    public static Collection<IRoom> getAllRooms() {
        return roomMap.values();
    }

}
