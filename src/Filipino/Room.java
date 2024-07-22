package Filipino;

import java.util.ArrayList;

/**
 * Room
 */
public class Room{
    private int name;
    private boolean available;
    private int price;
    private int bookCount = 0;
    private ArrayList<Integer> checkin = new ArrayList<>();
    private ArrayList<Integer> checkout = new ArrayList<>();

    public Room(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public boolean isAvailable(int checkin1, int checkout1) {
        available = true;
        for (int i = 0; i < bookCount; i++) {
            if(checkin.get(i) < checkout1 && checkout.get(i) > checkin1){
                available = false;
            }
        }
        return available;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void addBookCount() {
        bookCount++;
    }

    public int getcheckin(int index){
        return checkin.get(index);
    }

    public void setcheckin(int checkin){
        this.checkin.add(checkin);
    }

    public int getcheckout(int index){
        return checkout.get(index);
    }

    public void setcheckout(int checkout){
        this.checkout.add(checkout);
    }
}