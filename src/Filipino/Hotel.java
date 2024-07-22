package Filipino;

import java.util.ArrayList;

/**
 * Hotel
 */
public class Hotel {
    private String name;
    private int numberOfRooms;
    private int standardRooms;
    private int deluxeRooms;
    private int exclusiveRooms;
    private int price;
    private int totalEarnings = 0;
    private int roomCount = 1;
    private ArrayList<Reservation> reservation = new ArrayList<>();
    private ArrayList<Room> room = new ArrayList<>(50);

    public Hotel(String name, int standardRooms, int deluxeRooms, int exclusiveRooms) {
        this.name = name;
        numberOfRooms = standardRooms + deluxeRooms + exclusiveRooms;
        this.standardRooms = standardRooms;
        this.deluxeRooms = deluxeRooms;
        this.exclusiveRooms = exclusiveRooms;
        price = 1299;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void addRoomCount() {
        roomCount++;
    }

    public int getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(int totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void minusNumberOfRooms() {
        numberOfRooms--;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Room getroom(int index){
        return room.get(index);
    }

    public void setroom(Room room){
        this.room.add(room);
    }

    public void removeroom(int index){
        this.room.remove(index);
    }

    public Reservation getreservation(int index){
        return reservation.get(index);
    }

    public void setreservation(Reservation reservation){
        this.reservation.add(reservation);
    }

    public void removereservation(int index){
        this.reservation.remove(index);
    }

    public int getReservationCount() {
        return this.reservation.size();
    }
}
