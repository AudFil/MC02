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
    private ArrayList<Double> specialRates = new ArrayList<>();
    private ArrayList<Room> standardRooms = new ArrayList();
    private ArrayList<Room> deluxeRooms = new ArrayList();
    private ArrayList<Room> executiveRooms= new ArrayList();
    private ArrayList<ArrayList<Room>> room = new ArrayList();

    /**
     * Hotel
     * 0 = standard, 1 = deluxe, 2 = executive
     *
     * @param name
     * @param Nstandard
     * @param Ndeluxe
     * @param Nexecutive
     */
    public Hotel(String name, int Nstandard, int Ndeluxe, int Nexecutive) {
        this.name = name;
        room.add(standardRooms); //0
        room.add(deluxeRooms);   //1
        room.add(executiveRooms);//2
        addRooms(Nstandard, Ndeluxe, Nexecutive);
        price = 1299;

        for(int i = 0; i < 31; i++){
            specialRates.add(1.00);
        }
    }

    /**
     * Add Rooms
     * Adds how many rooms of each type
     *
     * @param Nstandard
     * @param Ndeluxe
     * @param Nexecutive
     */
    public void addRooms(int Nstandard, int Ndeluxe, int Nexecutive){
        int i;

        for(i = 0; i < Nstandard; i++){
            room.get(0).add(new Standard(0, roomCount++));
        }

        for(i = 0; i < Ndeluxe; i++){
            room.get(1).add(new Deluxe(1, roomCount++));
        }

        for(i = 0; i < Nexecutive; i++){
            room.get(2).add(new Executive(2, roomCount++));
        }
    }

    /**
     * Remove Rooms
     * Removes one certain type of room
     *
     * @param Nremove
     * @param roomType
     */
    public void removeRooms(int Nremove, int roomType){
        int i = 0;
        int skip = 0;
        int base = room.get(roomType).size();
        boolean stop = false;

        while(i < Nremove + skip && !stop) {
            if(i >= base){
                stop = true;
            }
            else if(room.get(roomType).get(skip).getReservation().isEmpty()){
                room.get(roomType).remove(skip);
            }
            else{
                skip++;
            }
            i++;
        }
    }

    /**
     * Add Reservation Count
     */
    public void addReservationCount() {
        reservationCount++;
    }

    /**
     * Minus Reservation Count
     */
    public void minusReservationCount() {
        reservationCount--;
    }

    /**
     * Add Special Rates
     *
     * @param rate
     * @param start
     * @param end
     */
    public void addspecialRates(double rate, int start, int end){
        start--;

        for(int i = start; i < end; i++){
            specialRates.set(i, rate);
        }
    }

    /**
     * Remove Special Rates
     * @param start
     * @param end
     */
    public void removespecialRates(int start, int end){
        start--;

        for(int i = start; i < end; i++){
            specialRates.set(i, 1.00);
        }
    }

    /**
     * Price For Day
     * @param date
     * @return
     */
    public double priceForDay(int date) {
        return price * specialRates.get(--date);
    }

    /**
     * Compute Earnings
     * @return
     */
    public double computeEarnings(){
        double earnings = 0;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < room.get(i).size(); j++){
                for (int k = 0; k < room.get(i).get(j).reservation.size(); k++){
                    earnings += computePrice(i, j, k);
                }
            }
        }
        return earnings;
    }

    /**
     * Compute Price
     * @param roomType
     * @param roomNum
     * @param reservationIndex
     * @return
     */
    public double computePrice(int roomType, int roomNum, int reservationIndex){
        double finalPrice = 0;
        double temp = 0;
        int i;

        int checkin = room.get(roomType).get(roomNum).reservation.get(reservationIndex).getCheckin();
        int checkout = room.get(roomType).get(roomNum).reservation.get(reservationIndex).getCheckout();

        if(room.get(roomType).get(roomNum).reservation.get(reservationIndex).isValidCode()){
            switch(room.get(roomType).get(roomNum).reservation.get(reservationIndex).getDiscountCode()){
                case 0:
                    for(i = checkin; i < checkout; i++){
                        temp = (priceForDay(i) + (priceForDay(i) * room.get(roomType).get(roomNum).getMultiplier())) + temp;
                    }
                    finalPrice = temp * 0.90; //10% discount
                    break;
                case 1:
                    for(i = checkin + 1; i < checkout; i++){ //First day free!
                        finalPrice = (priceForDay(i) + (priceForDay(i) * room.get(roomType).get(roomNum).getMultiplier())) + finalPrice;
                    }
                    break;
                case 2:
                    for(i = checkin; i < checkout; i++){
                        temp = (priceForDay(i) + (priceForDay(i) * room.get(roomType).get(roomNum).getMultiplier())) + temp;
                    }
                    finalPrice = temp * 0.93; //7% discount!
                    break;
                default:
                    for(i = checkin; i < checkout; i++){ //No code
                        finalPrice = (priceForDay(i) + (priceForDay(i) * room.get(roomType).get(roomNum).getMultiplier())) + finalPrice;
                    }
            }
        }
        else{ //No code
            for(i = checkin; i < checkout; i++){
                finalPrice = (priceForDay(i) + (priceForDay(i) * room.get(roomType).get(roomNum).getMultiplier())) + finalPrice;
            }
        }
        return finalPrice;
    }

    /**
     * Get Name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Price
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set Price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get Reservation Count
     * @return
     */
    public int getReservationCount() {
        return reservationCount;
    }

    /**
     * Get Room List
     * @return
     */
    public ArrayList<ArrayList<Room>> getroomList(){
        return room;
    }

    /**
     * Get Special Rates
     * @return
     */
    public ArrayList<Double> getSpecialRates(){
        return specialRates;
    }
}
