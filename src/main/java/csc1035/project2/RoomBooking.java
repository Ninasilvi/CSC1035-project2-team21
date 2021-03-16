package csc1035.project2;

import org.hibernate.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomBooking {

    List<Rooms> rooms;
    List<Rooms> bookedRooms = new ArrayList<>();
    List<Rooms> availableRooms = new ArrayList<>();
    static InputCheck ic = new InputCheck();
    File roomFile = new File("src\\main\\resources\\bookedRooms.cvs");

    public void listOfRooms() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        rooms = se.createQuery("FROM Rooms").list();

        se.getTransaction().commit();
        se.close();
    }

    // Books a room by adding it to bookedRooms list and recording it in bookedRooms.cvs file
    public void bookRooms() {
        UI.listOfRooms();
        UI.bookRoomsText();
        bookedRoomsFile();

        int choice = ic.get_int_input(1, rooms.size());
        Rooms room = rooms.get(choice - 1);
        boolean contains = false;

        for (Rooms r : bookedRooms) {
            if (room.compare(r)) {
                contains = true;
                break;
            }
        }

        if (contains) {
            UI.roomAlreadyBooked();
        } else {
            bookedRooms.add(room);
            writeToBookedRooms(room);
            UI.roomBookingConfirmation(room);
        }
    }

    // Saves a booked room to bookedRooms.cvs file
    public void writeToBookedRooms(Rooms room) {
        try {
            FileWriter bookedRoomWriter = new FileWriter(roomFile, true);
            bookedRoomWriter.write(room + "\n");
            bookedRoomWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    // Reads the bookedRooms.cvs file and matches the room to an existing recorded room
    // adding all booked rooms from the file to bookedRooms list
    public void bookedRoomsFile() {
        try {
            Scanner roomFileReader = new Scanner(roomFile);
            while (roomFileReader.hasNextLine()) {
                String line = roomFileReader.nextLine();
                String[] items = line.split("'");
                float roomNumber = Float.parseFloat(items[1]);
                for (Rooms room : rooms) {
                    if (room.getRoomNumber() == roomNumber) {
                        bookedRooms.add(room);
                        break;
                    }
                }
            }
            roomFileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    // Puts all available rooms into a list
    public void availableRooms() {
        listOfRooms();
        bookedRoomsFile();

        for (Rooms room : rooms) {
            if (!bookedRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
    }

}
