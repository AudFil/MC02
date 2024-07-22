package Filipino;

import java.util.ArrayList;

/**
 * Room
 */
public class Room{
    protected int roomNumber;
    protected boolean available;
    protected ArrayList<Reservation> reservation = new ArrayList<>();
    protected char roomType;

    public Room(char roomType){
        this.roomType = roomType;
    }

    /**
     * Methods
     */

    public void addReservation(Reservation reservation){
        this.reservation.add(reservation);
    }

    public void removeReservation(int index){
        this.reservation.remove(index);
    }

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

    public char getRoomType() {
        return roomType;
    }

    public boolean isAvailable() {
        return available;
    }

    public ArrayList<Reservation> getReservation() {
        return reservation;
    }
}