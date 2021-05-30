package model;

import java.util.HashMap;
import java.util.Map;

public enum RoomType {
    SINGLE (1),
    DOUBLE (2);

    private int numberOfBeds;

    private static final Map<Integer, RoomType> BY_NUMBER_OF_BEDS = new HashMap<Integer, RoomType>();

    static {
        for (RoomType roomType : values()) {
            BY_NUMBER_OF_BEDS.put(roomType.numberOfBeds, roomType);
        }
    }

    RoomType(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public static RoomType valueforNumberOfBeds(int numberOfBeds) {
        return BY_NUMBER_OF_BEDS.get(numberOfBeds);
    }

}
