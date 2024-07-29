package Filipino.Rooms;

import Filipino.Room;

public class Executive extends Room{

    public Executive(int roomType){
        super(roomType);
        multipler = 0.35;
    }

    @Override
    public double pricePerNight(double price){
        return price + (price * multipler);
    }
}
