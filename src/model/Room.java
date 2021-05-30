package model;

public class Room  implements IRoom {

    protected String roomNumber;
    protected Double price;
    protected RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }

    @Override
    public Boolean isFree() {
        if (this.price == (double) 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Room number: " + this.roomNumber + " " + this.enumeration + " bed room Price: $" + this.price;
    }


}
