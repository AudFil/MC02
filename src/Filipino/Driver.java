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
    private int executive;
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
                    executive = Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Exclusive Rooms: "));
                } catch (NumberFormatException o) {
                    valid = false;
                    JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                }

                if (validateRooms(standard + deluxe + executive) && valid) {
                    JOptionPane.showMessageDialog(null, "Hotel Created!", "Successful Creation", JOptionPane.INFORMATION_MESSAGE);
                } else if (!validateRooms(standard + deluxe + executive) && valid) {
                    JOptionPane.showMessageDialog(null, "Please check number of rooms! [1-50]", "Number of rooms", JOptionPane.ERROR_MESSAGE);
                    valid = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "A hotel with this name already exists!", "Exists", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
            if (valid) {
                createHotel(name, standard, deluxe, executive);
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
    public void createHotel(String hotelName, int standard, int deluxe, int executive) {
        hotel.add(hotel.size(), new Hotel(hotelName, standard, deluxe, executive)); //Adds a new hotel to arraylist
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
        int roomType = -1;
        int date;
        int[] gData = {-1, -1, -1}; //Type, Room, Reservation

        boolean valid;

        String msg;
        String gName;
        String[] viewOptions = {"Hotel Name", "Number of Rooms", "Estimated Earnings", "Number of free/booked rooms", "Room Information", "Hotel Reservations", "Special Dates"};

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
                    msg = "Number of Rooms: " + hotel.get(index).getroomList().size()
                            + "\nStandard Rooms: " + hotel.get(index).getroomList().get(0).size()
                            + "\nDeluxe Rooms: " + hotel.get(index).getroomList().get(1).size()
                            + "\nExecutive Rooms: " + hotel.get(index).getroomList().get(2).size();
                    JOptionPane.showMessageDialog(null, msg, "Number of Rooms", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 3: //Check the estimated earnings
                    JOptionPane.showMessageDialog(null, "Estimated Earnings: " + hotel.get(index).computeEarnings(), "Estimated Earnings", JOptionPane.INFORMATION_MESSAGE);
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
                            msg = "1 - Standard\n2 - Deluxe\n3 - Exclusive\nPlease enter a room type: ";
                            roomType = Integer.parseInt(JOptionPane.showInputDialog(msg));
                            roomType--; //Fixes Index
                            roomind = Integer.parseInt(JOptionPane.showInputDialog("Please enter a room  (1 - " + hotel.get(index).getroomList().get(roomType).size() + "): "));
                        } catch (NumberFormatException o) {
                            JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                            valid = true;
                        }
                    } while (!(roomind >= 1 && roomind <= hotel.get(index).getroomList().get(roomType).size()) && valid); //Input an existing room
                    roomind--;
                    roomInfo(index, roomind, roomType); //Displays room information
                    break;
                case 6: //Checks the information regarding an existing reservation
                    //If there is a reservation in the given hotel
                    if (hotel.get(index).getReservationCount() > 0) {
                        gName = JOptionPane.showInputDialog("Please enter reservation name: ");
                        searchGuest(index, gName, gData);

                        if (gData[0] != -1) {
                            msg = "Guest name: " + hotel.get(index).getroomList().get(gData[0]).get(gData[1]).getReservation().get(gData[2]).getreservationName()
                                    + "\nRoom type: " + (hotel.get(index).getroomList().get(gData[0]).get(gData[1]).getRoomType() + 1)
                                    + "\nGuest room: " + hotel.get(index).getroomList().get(gData[0]).get(gData[1]).getRoomNumber()
                                    + "\nCheck-in: " + hotel.get(index).getroomList().get(gData[0]).get(gData[1]).getReservation().get(gData[2]).getCheckin()
                                    + "\nCheck-out: " + hotel.get(index).getroomList().get(gData[0]).get(gData[1]).getReservation().get(gData[2]).getCheckout()
                                    + "\nTotal price: " + hotel.get(index).computePrice(gData[0], gData[1], gData[2]);
                            JOptionPane.showMessageDialog(null, msg, "Guest Information", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Guest not found", "Display Reservation", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    //No reservation are made in the hotel
                    else {
                        JOptionPane.showMessageDialog(null, "Please make some reservation first!", "No Reservations", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 7:
                    do{
                        try {
                            date = Integer.parseInt(JOptionPane.showInputDialog("Enter date: "));
                        }
                        catch (InputMismatchException o) {
                            date = 0;
                            JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                        }
                    }while(!(date >= 1 && date <= 31));
                    date--;

                    msg = "Modifier: " + hotel.get(index).getSpecialRates().get(date);
                    JOptionPane.showMessageDialog(null, msg, "View Special Dates", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    break;

            }

        }

    }

    /**
     * Manage Hotel
     * Manage an existing hotel
     */
    public void manageHotel() {
        int base;
        int index;
        int choice;
        boolean found;

        int SroomNum; //Standard
        int DroomNum; //Deluxe
        int EroomNum; //Executive
        int[] gData = {-1, -1, -1}; //Type, Room, Reservation
        double newprice;

        double rate;
        int startDate;
        int endDate;

        String newname;
        String[] manageOptions = {"Change Hotel Name", "Add Rooms", "Remove Rooms", "Update Pricing", "Remove Reservation", "Add Special Rates", "Remove Special Rates", "Remove Hotel"};

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
                    SroomNum = 0;
                    DroomNum = 0;
                    EroomNum = 0;
                    try {
                        SroomNum = Integer.parseInt(JOptionPane.showInputDialog("Current number of rooms: " + hotel.get(index).getroomList().size() + "\nHow many standard rooms would you like to add"));
                        DroomNum = Integer.parseInt(JOptionPane.showInputDialog("Current number of rooms: " + hotel.get(index).getroomList().size() + "\nHow many deluxe rooms would you like to add"));
                        EroomNum = Integer.parseInt(JOptionPane.showInputDialog("Current number of rooms: " + hotel.get(index).getroomList().size() + "\nHow many exclusive rooms would you like to add"));
                    } catch (InputMismatchException o) {
                        JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                    }

                    //If the number of rooms the user wants to add and current number will be less than or equal to 50
                    if (SroomNum + DroomNum + EroomNum + hotel.get(index).getroomList().size() <= 50 && (SroomNum != 0 || DroomNum != 0 || EroomNum != 0)) {

                        if (confirmation()) {
                            hotel.get(index).addRooms(SroomNum, DroomNum, EroomNum);
                        }
                    }
                    //If the input exceeds 50
                    else {
                        JOptionPane.showMessageDialog(null, "Number of rooms will exceed the limit (50)!", "Limit Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 3: //Remove rooms
                    SroomNum = 0;
                    DroomNum = 0;
                    EroomNum = 0;

                    //If there are more than 1 rooms in the hotel
                    if (hotel.get(index).getroomList().size() > 1) {
                        try {
                            SroomNum = Integer.parseInt(JOptionPane.showInputDialog("Current number of rooms: " + hotel.get(index).getroomList().size() + "\nHow many standard rooms would you like to remove"));
                            DroomNum = Integer.parseInt(JOptionPane.showInputDialog("Current number of rooms: " + hotel.get(index).getroomList().size() + "\nHow many deluxe rooms would you like to remove"));
                            EroomNum = Integer.parseInt(JOptionPane.showInputDialog("Current number of rooms: " + hotel.get(index).getroomList().size() + "\nHow many exclusive rooms would you like to remove"));
                        } catch (InputMismatchException o) {
                            JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                        }

                        //If the number of rooms the user wants to remove will not make the number of rooms 0
                        if (hotel.get(index).getroomList().size() - SroomNum - DroomNum - EroomNum > 0) {
                            if (confirmation()) {
                                base = hotel.get(index).getroomList().size();

                                hotel.get(index).removeRooms(SroomNum, 0);
                                hotel.get(index).removeRooms(DroomNum, 1);
                                hotel.get(index).removeRooms(EroomNum, 2);

                                if (hotel.get(index).getroomList().size() - SroomNum - DroomNum - EroomNum != base) {
                                    JOptionPane.showMessageDialog(null, "Unable to remove all rooms", "Remove Rooms", JOptionPane.ERROR_MESSAGE);
                                }

                                base = SroomNum + DroomNum + EroomNum;
                                JOptionPane.showMessageDialog(null, base + " room/s successfully removed!", "Remove Rooms", JOptionPane.INFORMATION_MESSAGE);
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
                    }
                    else {
                        do {
                            try {
                                newprice = Double.parseDouble(JOptionPane.showInputDialog("Please enter new room price (Should be larger or equal to 100): "));
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
                        searchGuest(index, JOptionPane.showInputDialog("Please enter reservation name: "), gData);

                        if(confirmation()){
                            if (gData[0] != -1) {
                                if (confirmation()) {
                                    hotel.get(index).getroomList().get(gData[0]).get(gData[1]).getReservation().remove(gData[2]);
                                }
                            }
                            hotel.get(index).minusReservationCount();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please make some reservation first!", "Remove Reservation", JOptionPane.WARNING_MESSAGE);
                    }

                    break;
                case 6: //Add Special Rate
                    startDate = 0;
                    endDate = 0;
                    rate = 0;
                    do{
                        try {
                            startDate = Integer.parseInt(JOptionPane.showInputDialog("Please enter rate start date: "));
                            endDate = Integer.parseInt(JOptionPane.showInputDialog("Please enter rate end date:"));
                            rate = Double.parseDouble(JOptionPane.showInputDialog("Please enter rate [Percent]: "));
                        } catch (InputMismatchException o) {
                            JOptionPane.showMessageDialog(null, "Please input numbers only!", "Misinput", JOptionPane.WARNING_MESSAGE);
                        }
                    }while(!(startDate >= 1 && endDate <= 31 && endDate > startDate && rate >= 50 && rate <= 150));

                    if(confirmation()){
                        rate/= 100;
                        hotel.get(index).addspecialRates(rate, startDate, endDate);
                    }
                    break;
                case 7: //Remove Special Rate
                    String msg;

                    startDate = 0;
                    endDate = 0;

                    do {
                        try{
                            startDate = Integer.parseInt(JOptionPane.showInputDialog("Enter starting date:"));
                            endDate = Integer.parseInt(JOptionPane.showInputDialog("Enter end date:"));
                        }
                        catch (InputMismatchException o){
                            JOptionPane.showMessageDialog(null, "Please input numbers only!", "Misinput", JOptionPane.WARNING_MESSAGE);
                        }
                    }while(!(startDate >= 1 && endDate <= 31 && endDate > startDate));

                    if(confirmation()){
                        hotel.get(index).removespecialRates(startDate, endDate);
                    }
                    break;
                case 8: //Remove hotel
                    if (confirmation()) {
                        hotel.remove(index);
                    }
                    break;
                default:
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
        int checkin = 0;
        int checkout = 0;
        int roomType;
        int dCode;
        boolean free;
        String msg;
        String rName;

        index = searchHotel(JOptionPane.showInputDialog("Enter Hotel Name to book at: "));

        if(index != -1){
            hotel.get(index).addReservationCount();

            rName = JOptionPane.showInputDialog("Please input reservation name: ");//Name of the person that reserved
            dCode = checkDiscountCode(JOptionPane.showInputDialog("Input discount code: \n[None] if you have no discount code"));

            do {
                try {
                    roomType = Integer.parseInt(JOptionPane.showInputDialog("1. Standard\n2. Deluxe\n3. Executive\n Please room type: "));
                } catch (InputMismatchException o) {
                    roomType = 0;
                    JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                }
            } while (!(roomType >= 1 && roomType <= 3)); //Gets a date from 1 to 30
            roomType--;

            do {
                try {
                    checkin = Integer.parseInt(JOptionPane.showInputDialog("Please input check-in date [1-31]: "));
                } catch (InputMismatchException o) {
                    JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                }
            } while (!(checkin >= 1 && checkin < 31)); //Gets a date from 1 to 30

            do {
                try {
                    checkout = Integer.parseInt(JOptionPane.showInputDialog("Please input check-out date [" + (checkin + 1) + "-31]: "));
                } catch (InputMismatchException o) {
                    JOptionPane.showMessageDialog(null, "Please enter numbers only!", "Misinput", JOptionPane.ERROR_MESSAGE);
                }
            } while (!(checkout > checkin && checkout <= 31)); //Gets a date from after the checkin till 31

            //Finds the room that can accomodate the user's chosen date
            free = false;
            i = 0;
            while (i < hotel.get(index).getroomList().get(roomType).size() && !free) {
                if (hotel.get(index).getroomList().get(roomType).get(i).isAvailable(checkin, checkout)) { //Checks if the room is available for the room
                    free = true;
                }
                i++;
            }
            i--; //Index of the free room

            if (free) {
                hotel.get(index).getroomList().get(roomType).get(i).getReservation().add(new Reservation(rName, checkin, checkout, dCode, hotel.get(index).getroomList().get(roomType).get(i).getRoomNumber()));
                //Msg contains the room number and the total price for the stay
                msg = String.format("You have successfully reserved Room " + hotel.get(index).getroomList().get(roomType).get(i).getReservation().get(hotel.get(index).getroomList().get(roomType).get(i).getReservation().size() - 1).getRoomNumber() + "!"
                        + "\nTotal price is: %.2f", hotel.get(index).computePrice(roomType, i, hotel.get(index).getroomList().get(roomType).get(i).getReservation().size() - 1));
                JOptionPane.showMessageDialog(null, msg, hotel.get(index).getName() + " Booking", JOptionPane.INFORMATION_MESSAGE);

                hotel.get(index).addReservationCount();
            } else {
                msg = "Sorry, all rooms are unavailable for that time!";
                JOptionPane.showMessageDialog(null, msg, hotel.get(index).getName() + " Booking", JOptionPane.INFORMATION_MESSAGE);
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

    public void searchGuest(int index, String gName, int[] data) {
        data[0] = -1; //Room Type
        data[1] = -1; //Room Number
        data[2] = -1; //Reservation Number

        if (hotel.get(index).getReservationCount() > 0) {
            for (int i = 0; i < 3; i++) { //Go through each type of room
                for(int j = 0; j < hotel.get(index).getroomList().get(i).size(); j++){ //How many rooms for each type to go through
                    for(int k = 0; k < hotel.get(index).getroomList().get(i).get(j).getReservation().size(); k++){ //How many reservations in each room
                        if (gName.compareTo(hotel.get(index).getroomList().get(i).get(j).getReservation().get(k).getreservationName()) == 0) {
                            data[0] = i; //Room Type
                            data[1] = j; //Room Number
                            data[2] = k; //Reservation Number
                        }
                    }
                }
            }
        }
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
    public void roomInfo(int index, int roomindex, int roomType) {
        String msg;

        msg = "Room " + hotel.get(index).getroomList().get(roomType).get(roomindex).getRoomNumber() + "\nPrice per Night: " + hotel.get(index).getroomList().get(roomType).get(roomindex).pricePerNight(hotel.get(index).getPrice());
        JOptionPane.showMessageDialog(null, msg, "Room Information", JOptionPane.INFORMATION_MESSAGE);

        if (hotel.get(index).getroomList().get(roomType).get(roomindex).getReservation().size() == 0) {
            JOptionPane.showMessageDialog(null, "No bookings has been made!", "Room Booked Dates", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < hotel.get(index).getroomList().get(roomType).get(roomindex).getReservation().size(); i++) {
                msg = "Checkin: " + hotel.get(index).getroomList().get(roomType).get(roomindex).getReservation().get(i).getCheckin() + "- Checkout:" + hotel.get(index).getroomList().get(roomType).get(roomindex).getReservation().get(i).getCheckout();
                JOptionPane.showMessageDialog(null, msg, "Room Booked Dates", JOptionPane.INFORMATION_MESSAGE);
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

        int freeRooms = hotel.get(index).getroomList().size();
        int bookedRooms = 0;

        for (int i = 0; i < 3; i++) { //Each room type
            for (int j = 0; j < hotel.get(index).getroomList().get(i).size(); j++) { //Number of rooms for each room type
                for(int k = 0; k < hotel.get(index).getroomList().get(i).get(j).getReservation().size(); k++){ //Reservation in each room
                    checkout = hotel.get(index).getroomList().get(i).get(j).getReservation().get(k).getCheckout(); //For readability
                    checkin = hotel.get(index).getroomList().get(i).get(j).getReservation().get(k).getCheckin(); //For readability

                    if (checkout > day && checkin <= day) {
                        bookedRooms++;
                        freeRooms--;
                    }
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