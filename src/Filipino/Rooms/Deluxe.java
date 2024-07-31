package Filipino.Rooms;

import Filipino.Room;

public class Deluxe extends Room{

    public Deluxe(int roomType, int roomNumber){
        super(roomType, roomNumber);
        multiplier = 0.2;
    }

    @Override
    public double pricePerNight(double price){
        return price + (price * multiplier);
    }
}
