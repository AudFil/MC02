package Filipino.Rooms;

import Filipino.Room;

public class Deluxe extends Room{
    private double multipler = 0.2;

    public Deluxe(char roomType){
        super(roomType);
    }

    @Override
    public double pricePerNight(double price){
        return price + (price * multipler);
    }

    @Override
    public double computePrice(int reservationIndex, double price){
        double finalPrice;
        double temp;

        int checkin = reservation.get(reservationIndex).getCheckin();
        int checkout = reservation.get(reservationIndex).getCheckout();

        if(reservation.get(reservationIndex).isValidCode()){
            switch(reservation.get(reservationIndex).getDiscountCode()){
                case 0:
                    temp = (checkout - checkin) * (price * multipler);
                    finalPrice = temp - temp * 0.10;
                    break;
                case 1:
                    finalPrice = (checkout - checkin - 1) * (price * multipler);
                    break;
                case 2:
                    temp = (checkout - checkin) * (price * multipler);
                    finalPrice = temp - temp * 0.07;
                    break;
                default:
                    finalPrice = (checkout - checkin) * (price * multipler);
            }
        }
        else{
            finalPrice = (checkout - checkin) * (price * multipler);
        }

        return finalPrice;
    }
}
