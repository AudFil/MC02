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

    /**
     * Reservation
     *
     * @param name
     * @param checkin
     * @param checkout
     * @param discountCode
     * @param roomNumber
     */
    public Reservation(String name, int checkin, int checkout, int discountCode, int roomNumber) {
        this.reservationName = name;
        this.checkin = checkin;
        this.checkout = checkout;
        this.discountCode = discountCode;
        this.roomNumber = roomNumber;

        checkCode();
    }

    /**
     * Check Code
     * Checks whether a discount code is valid or not given the checkin and checkout date
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
     * Get Room Number
     * @return
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Get Discount Code
     * @return
     */
    public int getDiscountCode() {
        return discountCode;
    }

    /**
     * Get Checkout
     * @return
     */
    public int getCheckout() {
        return checkout;
    }

    /**
     * Get Check in
     * @return
     */
    public int getCheckin() {
        return checkin;
    }

    /**
     * Get Reservation Name
     * @return
     */
    public String getreservationName() {
        return reservationName;
    }

    /**
     * Is Valid Code
     * @return
     */
    public boolean isValidCode() {
        return validCode;
    }
}