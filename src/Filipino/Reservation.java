package Filipino;

/**
 * Reservation
 */
public class Reservation {
    private String name;
    private int checkin;
    private int checkout;
    private Room room;
    private int price;
    private int totalprice;

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void computetotalprice(){
        totalprice = (checkout - checkin) * price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCheckin() {
        return checkin;
    }

    public void setCheckin(int checkin) {
        this.checkin = checkin;
    }

    public int getCheckout() {
        return checkout;
    }

    public void setCheckout(int checkout) {
        this.checkout = checkout;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}