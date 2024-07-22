package Filipino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Driver implements ActionListener {
    private ArrayList<Hotel> hotel = new ArrayList<>(); //List of hotels

    private String name;
    private int standard;
    private int deluxe;
    private int exclusive;
    private boolean valid;

    private JFrame frame = new JFrame();

    private JPanel titlePanel = new JPanel();
    private JLabel title = new JLabel("Hotel Reservation System");

    private JPanel buttonPanel = new JPanel();
    private JButton createButton = new JButton("Create Hotel");
    private JButton viewButton = new JButton("View Hotel");
    private JButton manageButton = new JButton("Manage Hotel");
    private JButton simulateButton = new JButton("Simulate Booking");

    public Driver() {
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.BOTTOM);
        title.setFont(new Font("Arial", Font.PLAIN, 30));

        titlePanel.setBounds(400, 100, 800, 500);
        titlePanel.add(title);

        createButton.setBounds(150, 0, 100, 50);
        createButton.addActionListener(this);

        viewButton.setBounds(150, 300, 100, 50);
        viewButton.addActionListener(this);

        manageButton.setBounds(150, 600, 100, 50);
        manageButton.addActionListener(this);

        simulateButton.setBounds(150, 900, 100, 50);
        simulateButton.addActionListener(this);

        buttonPanel.add(createButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(manageButton);
        buttonPanel.add(simulateButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hotel Reservation System");
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.setSize(700, 200);
        frame.setVisible(true);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(titlePanel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            valid = true;
            name = JOptionPane.showInputDialog("Enter Hotel Name: ");

            if (searchHotelName(name)) {
                try {
                    standard = Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Standard Rooms: "));
                    deluxe = Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Deluxe Rooms: "));
                    exclusive = Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Exclusive Rooms: "));
                } catch (NumberFormatException o) {
                    valid = false;
                    JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                }

                if (validateRooms(standard + deluxe + exclusive) && valid) {
                    JOptionPane.showMessageDialog(null, "Hotel Created!", "Successful Creation", JOptionPane.INFORMATION_MESSAGE);
                } else if (!validateRooms(standard + deluxe + exclusive) && valid) {
                    JOptionPane.showMessageDialog(null, "Please check number of rooms! [1-50]", "Number of rooms", JOptionPane.ERROR_MESSAGE);
                    valid = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "A hotel with this name already exists!", "Exists", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
            if (valid) {
                createHotel(name, standard, deluxe, exclusive);
            }
        } else if (e.getSource() == viewButton) {
            if (hotel.size() > 0) {
                viewHotel();
            } else {
                JOptionPane.showMessageDialog(null, "Please create at least one hotel!", "No Hotels", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == manageButton) {
            if (hotel.size() > 0) {
                manageHotel();
            } else {
                JOptionPane.showMessageDialog(null, "Please create at least one hotel!", "No Hotels", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == simulateButton) {
            if (hotel.size() > 0) {
                simulateBooking();
            } else {
                JOptionPane.showMessageDialog(null, "Please create at least one hotel!", "No Hotels", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////Main System///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Create Hotel
     * Create a new hotel
     */
    public void createHotel(String hotelName, int standard, int deluxe, int exclusive) {
        hotel.add(hotel.size(), new Hotel(hotelName, standard, deluxe, exclusive)); //Adds a new hotel to arraylist
    }

    /**
     * View Hotel
     * View an existing hotel
     */
    public void viewHotel() {
        int index;
        int choice;
        int day = 0;
        int roomind = -1;
        int gIndex = -1;

        boolean valid;

        String gName;
        String[] viewOptions = {"Hotel Name", "Number of Rooms", "Estimated Earnings", "Number of free/booked rooms", "Room Information", "Hotel Reservations"};

        name = JOptionPane.showInputDialog("Enter Hotel Name to view: ");
        index = searchHotel(name);

        if (index != -1) {

            choice = JOptionPane.showOptionDialog(null, "View Hotel Options: ", "View Hotel", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, viewOptions, 0);
            choice++;
            //View actions for the hotel
            switch (choice) {
                case 1: //Check the hotel name
                    JOptionPane.showMessageDialog(null, "Hotel: " + hotel.get(index).getName(), "Hotel Name", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2: //Check the number of rooms
                    JOptionPane.showMessageDialog(null, "Number of Rooms: " + hotel.get(index).getNumberOfRooms(), "Number of Rooms", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 3: //Check the estimated earnings
                    JOptionPane.showMessageDialog(null, "Estimated Earnings: " + hotel.get(index).getTotalEarnings(), "Estimated Earnings", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 4: //Checks the number of free and booked rooms in a specific day
                    do {
                        valid = false;
                        try {
                            day = Integer.parseInt(JOptionPane.showInputDialog("Enter day from 1- 31: "));
                        } catch (InputMismatchException o) {
                            JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                            valid = true;
                        }
                    } while (!(day >= 1 && day <= 31) && valid);

                    roomAvailability(index, day);
                    break;
                case 5: //Gets the information of one specific room
                    do {
                        valid = false;
                        try {
                            roomind = Integer.parseInt(JOptionPane.showInputDialog("Please enter a room  (1 - " + hotel.get(index).getNumberOfRooms() + "): "));
                        } catch (NumberFormatException o) {
                            JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                            valid = true;
                        }
                    } while (!(roomind >= 1 && roomind <= hotel.get(index).getNumberOfRooms()) && valid); //Input an existing room
                    roomind--;
                    roomInfo(index, roomind); //Displays room information
                    break;
                case 6: //Checks the information regarding an existing reservation
                    //If there is a reservation in the given hotel
                    if (hotel.get(index).getReservationCount() > 0) {
                        gName = JOptionPane.showInputDialog("Please enter reservation name: ");
                        gIndex = searchGuest(index, gName);

                        if (gIndex != -1) {
                            JOptionPane.showMessageDialog(null,
                                    "Guest name: " + hotel.get(index).getreservation(gIndex).getName() +
                                            "\nGuest room: " + hotel.get(index).getreservation(gIndex).getRoom().getName()
                                            + "\nCheck-in: " + hotel.get(index).getreservation(gIndex).getCheckin()
                                            + "\nCheck-out: " + hotel.get(index).getreservation(gIndex).getCheckout()
                                            + "\nTotal price: " + hotel.get(index).getreservation(gIndex).getTotalprice()
                                            + "\nPrice per night: " + hotel.get(index).getPrice(), "Guest Information", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Guest not found", "Display Reservation", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    //No reservation are made in the hotel
                    else {
                        JOptionPane.showMessageDialog(null, "Please make some reservation first!", "No Reservations", JOptionPane.ERROR_MESSAGE);
                    }
            }

        }

    }

    /**
     * Manage Hotel
     * Manage an existing hotel
     */
    public void manageHotel() {
        int j;
        int base;
        int index;
        int choice;
        boolean found;

        int newprice;
        int roomNum;
        int resname;
        int skip;
        boolean stop = false;

        String newname;
        String[] manageOptions = {"Change Hotel Name", "Add Rooms", "Remove Rooms", "Update Pricing", "Remove Reservation", "Remove Hotel", "Date Price Modifiers"};

        name = JOptionPane.showInputDialog("Enter Hotel Name to manage: ");
        index = searchHotel(name);

        //If there is at least one hotel
        if (index != -1) {
            choice = JOptionPane.showOptionDialog(null, "Manage Hotel Options: ", "Manage Hotel", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, manageOptions, 0);
            choice++;

            switch (choice) {
                case 1: //Change hotel name
                    newname = JOptionPane.showInputDialog("Enter New Hotel Name: ");

                    found = searchHotelName(newname);

                    //If the new name is unique
                    if (!found) {
                        //Last prompt before the change
                        if (confirmation()) {
                            hotel.get(index).setName(newname);
                        }
                    } else { //If the user input hotel name already exists
                        JOptionPane.showMessageDialog(null, "Hotel name is already in use", "Change Hotel Name", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 2: //Add rooms
                    roomNum = 0;
                    try {
                        roomNum = Integer.parseInt(JOptionPane.showInputDialog("How many rooms would you like to add"));
                    } catch (InputMismatchException o) {
                        JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                    }

                    //If the number of rooms the user wants to add and current number will be less than or equal to 50
                    if (roomNum + hotel.get(index).getNumberOfRooms() <= 50 && roomNum != 0) {

                        if (confirmation()) {
                            iniRooms(index);
                            hotel.get(index).setNumberOfRooms(roomNum + hotel.get(index).getNumberOfRooms());
                        }
                    }
                    //If the input exceeds 50
                    else {
                        JOptionPane.showMessageDialog(null, "Number of rooms will exceed the limit (50)!", "Limit Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 3: //Remove rooms
                    roomNum = 0;
                    //If there are more than 1 rooms in the hotel
                    if (hotel.get(index).getNumberOfRooms() > 1) {
                        try {
                            roomNum = Integer.parseInt(JOptionPane.showInputDialog("Current number of rooms: " + hotel.get(index).getNumberOfRooms() + "\nHow many rooms would you like to remove: "));
                        } catch (InputMismatchException o) {
                            JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                        }

                        //If the number of rooms the user wants to remove will not make the number of rooms 0
                        if (hotel.get(index).getNumberOfRooms() - roomNum > 0 && roomNum > 1) {
                            if (confirmation()) {
                                skip = 0;
                                j = 0;
                                base = hotel.get(index).getNumberOfRooms();

                                while (j < roomNum + skip && !stop) {
                                    if (j >= base) {
                                        stop = true;
                                    } else if (hotel.get(index).getroom(skip).getBookCount() == 0) {
                                        hotel.get(index).removeroom(skip);
                                        hotel.get(index).minusNumberOfRooms();
                                    } else {
                                        skip++;
                                    }
                                    j++;
                                }

                                if (stop) {
                                    JOptionPane.showMessageDialog(null, "Unable to remove all rooms", "Remove Rooms", JOptionPane.ERROR_MESSAGE);
                                }

                                JOptionPane.showMessageDialog(null, roomNum + " room/s successfully removed!", "Remove Rooms", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Rooms not removed", "Remove Rooms", JOptionPane.INFORMATION_MESSAGE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Number of rooms cannot be 0!", "Remove Rooms", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No rooms to be removed!", "Remove Rooms", JOptionPane.INFORMATION_MESSAGE);
                    }


                    break;
                case 4: //Update room pricing
                    newprice = hotel.get(index).getPrice();

                    if (hotel.get(index).getReservationCount() > 0) {
                        JOptionPane.showMessageDialog(null, "Cannot change room pricing: There are reservation!", "Update Price", JOptionPane.WARNING_MESSAGE);
                    } else {
                        do {
                            try {
                                newprice = Integer.parseInt(JOptionPane.showInputDialog("Please enter new room price (Should be larger or equal to 100): "));
                            } catch (InputMismatchException o) {
                                JOptionPane.showMessageDialog(null, "Please input numbers only!", "Misinput", JOptionPane.WARNING_MESSAGE);
                            }
                        } while (newprice < 100);

                        if (confirmation()) {
                            hotel.get(index).setPrice(newprice);
                        }
                    }
                    break;
                case 5: //Remove a reservation
                    if (hotel.get(index).getReservationCount() > 0) {
                        resname = searchGuest(index, JOptionPane.showInputDialog("Please enter reservation name: "));

                        if (resname != -1) {
                            if (confirmation()) {
                                hotel.get(index).setTotalEarnings(hotel.get(index).getTotalEarnings() - hotel.get(index).getreservation(resname).getTotalprice());
                                hotel.get(index).removereservation(resname);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please make some reservation first!", "Remove Reservation", JOptionPane.WARNING_MESSAGE);
                    }

                    break;
                case 6: //Remove hotel
                    if (confirmation()) {
                        hotel.remove(index);
                    }
                    break;
                case 7:
                    break;
            }
        }
    }

    /**
     * Simulate Booking
     * Make reservation for hotels
     */
    public void simulateBooking() {
        int i;
        int index;
        int reservationIndex;
        int checkin = 0;
        int checkout = 0;
        boolean free;

        index = searchHotel(JOptionPane.showInputDialog("Enter Hotel Name to view: "));

        if(index != -1){
            hotel.get(index).setreservation(new Reservation()); //Creates a new reservation
            reservationIndex = hotel.get(index).getReservationCount() - 1;

            hotel.get(index).getreservation(reservationIndex).setName(JOptionPane.showInputDialog("Please input reservation name: "));//Name of the person that reserved
            //checkDiscountCode(JOptionPane.showInputDialog("Input discount code: \n[None] if you have no discount code"));

            do {
                try {
                    checkin = Integer.parseInt(JOptionPane.showInputDialog("Please input check-in date [1-31]: "));
                } catch (InputMismatchException o) {
                    JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                }
            } while (!(checkin >= 1 && checkin < 31)); //Gets a date from 1 to 30
            hotel.get(index).getreservation(reservationIndex).setCheckin(checkin); //Setter

            checkin++;
            do {
                try {
                    checkout = Integer.parseInt(JOptionPane.showInputDialog("Please input check-out date [" + checkin + "-31]: "));
                } catch (InputMismatchException o) {
                    JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                }
            } while (!(checkout > checkin && checkout <= 31)); //Gets a date from after the checkin till 31
            hotel.get(index).getreservation(reservationIndex).setCheckout(checkout); //Setter

            free = false;
            i = 0;
            while (i < hotel.get(index).getNumberOfRooms() && !free) {
                if (hotel.get(index).getroom(i).isAvailable(checkin, checkout)) { //Checks if the room is available for the room
                    free = true;
                }
                i++;
            }
            i--;

            if (free) {
                hotel.get(index).getroom(i).setcheckin(checkin); //Adds checkin to list
                hotel.get(index).getroom(i).setcheckout(checkout); //Adds checkout to list

                hotel.get(index).getreservation(reservationIndex).setRoom(hotel.get(index).getroom(i)); //Sets the room
                hotel.get(index).getreservation(reservationIndex).setPrice(hotel.get(index).getPrice()); //Sets the reservation price as the hotel room price
                hotel.get(index).getreservation(reservationIndex).computetotalprice(); //Computes for the price

                JOptionPane.showMessageDialog(null,
                        "You have successfully reserved Room " + hotel.get(index).getroom(i).getName() + "!"
                                + "\nTotal price is: " + hotel.get(index).getreservation(reservationIndex).getTotalprice()
                        , hotel.get(index).getName() + " Booking", JOptionPane.INFORMATION_MESSAGE);

                hotel.get(index).setTotalEarnings(hotel.get(index).getTotalEarnings() +
                        hotel.get(index).getreservation(reservationIndex).getTotalprice()); //Adds it to the total earnings
                hotel.get(index).getroom(i).addBookCount();
            } else {
                hotel.get(index).removereservation(i);
                JOptionPane.showMessageDialog(null, "Sorry, all rooms are booked for that time!", hotel.get(index).getName() + " Booking", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////HELPING FUNCTIONS//////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int searchHotel(String hotelName) {
        int index = -1;
        if (hotel.size() > 0) {
            for (int i = 0; i < hotel.size(); i++) {
                if (hotelName.compareTo(hotel.get(i).getName()) == 0) {
                    index = i;
                }
            }
        }
        return index;
    }

    public int searchGuest(int index, String gName) {
        int gindex = -1;
        if (hotel.get(index).getReservationCount() > 0) {
            for (int i = 0; i < hotel.get(index).getRoom(); i++) {
                for(int j = 0){
                    for(int k = 0;){
                        if (gName.compareTo(hotel.get(index).getRoom(1, i).getReservation().get(i).getreservationName()) == 0) {
                            gindex = i;
                        }
                    }
                }
            }
        }
        return gindex;
    }

    public boolean validateRooms(int numOfRooms) {
        return numOfRooms <= 50 && numOfRooms >= 1;
    }

    public boolean searchHotelName(String hotelName) {
        boolean valid = true;

        if (hotel.size() > 0) {
            for (int i = 0; i < hotel.size(); i++) {
                if (hotelName.compareTo(hotel.get(i).getName()) == 0) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Room Info
     * Gets information of the room based on the chosen room and the hotel
     *
     * @param index
     * @param roomindex
     */
    public void roomInfo(int index, int roomindex) {
        JOptionPane.showMessageDialog(null, "Room " + hotel.get(index).getroom(roomindex).getName() + "\nPrice per Night: " + hotel.get(index).getPrice(), "Room Information", JOptionPane.INFORMATION_MESSAGE);

        if (hotel.get(index).getroom(roomindex).getBookCount() == 0) {
            JOptionPane.showMessageDialog(null, "No bookings has been made!", "Room Booked Dates", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < hotel.get(index).getroom(roomindex).getBookCount(); i++) {
                JOptionPane.showMessageDialog(null, "Checkin: " + hotel.get(index).getroom(roomindex).getcheckin(i) + "- Checkout:" + hotel.get(index).getroom(roomindex).getcheckout(i), "Room Booked Dates", JOptionPane.INFORMATION_MESSAGE);
            }
        }


    }

    /**
     * Room Availability
     * Gets the number of free and booked rooms for a certain day
     *
     * @param index
     * @param day
     */
    public void roomAvailability(int index, int day) {
        int checkout;
        int checkin;

        int freeRooms = hotel.get(index).getNumberOfRooms();
        int bookedRooms = 0;

        for (int i = 0; i < hotel.get(index).getNumberOfRooms(); i++) {
            for (int j = 0; j < hotel.get(index).getroom(i).getBookCount(); j++) {
                checkout = hotel.get(index).getroom(i).getcheckout(j); //For readability
                checkin = hotel.get(index).getroom(i).getcheckin(j); //For readability
                if (checkout > day && checkin <= day) {
                    bookedRooms++;
                    freeRooms--;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Free rooms for day " + day + ": " + freeRooms + "\nBooked rooms for day " + day + ": " + bookedRooms, "Room Availabilty on " + day, JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean confirmation() {
        int choice = JOptionPane.showOptionDialog(null, "Are you sure you want apply this change?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);

        if (choice == 0) {
            JOptionPane.showMessageDialog(null, "Changes saved", "Changes", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        JOptionPane.showMessageDialog(null, "Changes have not been saved", "Changes", JOptionPane.INFORMATION_MESSAGE);
        return false;
    }

    public int checkDiscountCode(String code){
        int codeType;

        switch(code){
            case "I_WORK_HERE":
                codeType = 0;
                break;
            case "STAY4_GET1":
                codeType = 1;
                break;
            case "PAYDAY":
                codeType = 2;
                break;
            default:
                codeType = -1;
        }
        return codeType;
    }

}