package Filipino.Rooms;

import Filipino.Room;

public class Standard extends Room{

    public Standard(char roomType){
        super(roomType);
    }

    public double computePrice(int reservationIndex, double price){
        double finalPrice;
        double temp;

        int checkin = reservation.get(reservationIndex).getCheckin();
        int checkout = reservation.get(reservationIndex).getCheckout();

        if(reservation.get(reservationIndex).isValidCode()){
            switch(reservation.get(reservationIndex).getDiscountCode()){
                case 0:
                    temp = (checkout - checkin) * price;
                    finalPrice = temp - temp * 0.10;
                    break;
                case 1:
                    finalPrice = (checkout - checkin - 1) * price;
                    break;
                case 2:
                    temp = (checkout - checkin) * price;
                    finalPrice = temp - temp * 0.07;
                    break;
                default:
                    finalPrice = (checkout - checkin) * price;
            }
        }
        else{
            finalPrice = (checkout - checkin) * price;
        }

        return finalPrice;
    }
}
