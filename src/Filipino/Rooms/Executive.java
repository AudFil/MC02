package Filipino.Rooms;

import Filipino.Room;

public class Executive extends Room{

    public Executive(int roomType, int roomNumber){
        super(roomType, roomNumber);
        multiplier = 0.35;
    }

    @Override
    public double pricePerNight(double price){
        return price + (price * multiplier);
    }
}
