package Filipino;

/**
 * Reservation
 */
public class Reservation {
    private String reservationName;
    private int checkin;
    private int checkout;
    private int discountCode;
    private int roomNumber;
    private boolean validCode;

    public Reservation(String name, int checkin, int checkout, int discountCode, int roomNumber) {
        this.reservationName = name;
        this.checkin = checkin;
        this.checkout = checkout;
        this.discountCode = discountCode;
        this.roomNumber = roomNumber;

        checkCode();
    }

    /**
     * Methods
     */

    public void checkCode(){
        validCode = true;

        switch (discountCode){
            case 1:
                if(checkout - checkin < 5){
                    validCode = false;
                }
            case 2:
                if(checkout % 15 == 0){
                    validCode = false;
                }
        }
    }

    /**
     * Getters and Setters
     */

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getDiscountCode() {
        return discountCode;
    }

    public int getCheckout() {
        return checkout;
    }

    public int getCheckin() {
        return checkin;
    }

    public String getreservationName() {
        return reservationName;
    }

    public boolean isValidCode() {
        return validCode;
    }
}