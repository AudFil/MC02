package Filipino;

import java.util.ArrayList;

/**
 * Room
 */
public class Room{
    protected int roomNumber;
    protected int roomType;
    protected double multipler;
    protected boolean available;
    protected ArrayList<Reservation> reservation = new ArrayList<>();

    public Room(int roomType){
        this.roomType = roomType;
    }

    /**
     * Methods
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
     * Getters and Setters
     */

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomType() {
        return roomType;
    }

    public ArrayList<Reservation> getReservation() {
        return reservation;
    }

    public double pricePerNight(double price) {
        return price;
    }

    public double getMultipler() {
        return multipler;
    }
}