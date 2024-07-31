package Filipino;

import java.util.ArrayList;

/**
 * Room
 */
public class Room{
    protected int roomNumber;
    protected int roomType;
    protected double multiplier;
    protected boolean available;
    protected ArrayList<Reservation> reservation = new ArrayList<>();

    /**
     * Room
     *
     * @param roomType
     */
    public Room(int roomType, int roomNumber){
        this.roomType = roomType;
        this.roomNumber = roomNumber;
    }

    /**
     * Is Available
     * Checks whether a room for a given checkin and checkout day is available or not
     *
     * @param checkin
     * @param checkout
     * @return
     */
    public boolean isAvailable(int checkin, int checkout) {
        available = true;
        for (int i = 0; i < reservation.size(); i++) {
            if(reservation.get(i).getCheckin() < checkout && reservation.get(i).getCheckout() > checkin){
                available = false;
            }
        }
        return available;
    }

    /**
     * Get Room Number
     *
     * @return
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Set Room Number
     *
     * @param roomNumber
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Get Room Type
     *
     * @return
     */
    public int getRoomType() {
        return roomType;
    }

    /**
     * Get Reservation
     *
     * @return
     */
    public ArrayList<Reservation> getReservation() {
        return reservation;
    }

    /**
     * Price per Night
     *
     * @param price
     * @return
     */
    public double pricePerNight(double price) {
        return price;
    }

    /**
     * Get Multiplier
     *
     * @return
     */
    public double getMultiplier() {
        return multiplier;
    }
}