package Filipino;

import Filipino.Rooms.Deluxe;
import Filipino.Rooms.Executive;
import Filipino.Rooms.Standard;

import java.util.ArrayList;

/**
 * Hotel
 */
public class Hotel {
    private String name;
    private double price;
    private int roomCount = 1;
    private int reservationCount = 0;
    private ArrayList<Room> standardRooms = new ArrayList();
    private ArrayList<Room> deluxeRooms = new ArrayList();
    private ArrayList<Room> executiveRooms= new ArrayList();
    private ArrayList<ArrayList<Room>> room = new ArrayList();

    //0 = standard, 1 = deluxe, 2 = executive
    public Hotel(String name, int Nstandard, int Ndeluxe, int Nexecutive) {
        this.name = name;
        room.add(standardRooms); //0
        room.add(deluxeRooms);   //1
        room.add(executiveRooms);//2
        addRooms(Nstandard, Ndeluxe, Nexecutive);
        price = 1299;
    }

    /**
     * Methods
     */
    public void addRooms(int Nstandard, int Ndeluxe, int Nexecutive){
        int i;

        for(i = room.get(0).size(); i < Nstandard; i++){
            room.get(0).add(new Standard('S'));
            room.get(0).get(i).setRoomNumber(roomCount++);
        }

        for(i = room.get(1).size(); i < Ndeluxe; i++){
            room.get(1).add(new Deluxe('D'));
            room.get(1).get(i).setRoomNumber(roomCount++);
        }

        for(i = room.get(2).size(); i < Nexecutive; i++){
            room.get(2).add(new Executive('E'));
            room.get(2).get(i).setRoomNumber(roomCount++);
        }
    }

    public void removeRooms(int Nremove, int roomType){
        int i = 0;
        int skip = 0;
        int base = room.size();
        boolean stop = false;

        while(i < Nremove + skip && !stop) {
            if(i >= base){
                stop = true;
            }
            else if(room.get(roomType).get(skip).getReservation().size() == 0){
                room.get(roomType).remove(skip);
            }
            else{
                skip++;
            }
            i++;
        }
    }

    public void addReservationCount() {
        reservationCount++;
    }

    public void removeReservationCount() {
        reservationCount--;
    }

    /**
     * Getters and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public int getReservationCount() {
        return reservationCount;
    }

    public Room getRoom(int roomType, int index){
        return room.get(roomType).get(index);
    }
}
