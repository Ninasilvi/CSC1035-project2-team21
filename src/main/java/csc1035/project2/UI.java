package csc1035.project2;

import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class UI {

    static Scanner s = new Scanner(System.in);
    static Timetable t = new Timetable();

    public static void main(String[] args) {

        printMenu();
    }

    // Print Menu
    public static void printMenu () {

        System.out.println("\nPlease enter an option [1-6]:");
        System.out.println("1 - List Of Students Taking a Specific Module");
        System.out.println("2 - List of Staff");
        System.out.println("3 - List of Module Requirements");
        System.out.println("4 - Allow Create Timetable");
        System.out.println("5 - Producing Timetable");
        System.out.println("6 - Exit \n");

        if (s.hasNextInt()) {
            int choice = s.nextInt();

            switch (choice) {
                case 1 -> listOfStudentsChoice();
                case 2 -> t.listOfStaff();
                case 3 -> t.listOfModuleReq();
                case 4 -> t.allowCreateTimetable();
                case 5 -> t.producingTimetable();
                case 6 -> System.exit(420);
                default -> {
                    System.out.println("\nPlease enter one of the choices.");
                    printMenu();
                }
            }
        } else {
            System.out.println("\nPlease enter an integer.");
            printMenu();
        }
    }

    public static void listOfStudentsChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();

        se.beginTransaction();
        List<Module> modules = se.createQuery("FROM Module").list();
        se.getTransaction().commit();

        // Print results
        System.out.println("Please select a module:\n");

        for (int i = 0; i < modules.size(); i++) {
            System.out.println(i + 1 + " - " + modules.get(i));
        }

        int choice = 0;

        // Checks for input validity
        do{
            if (s.hasNextInt()) {
                choice = s.nextInt();

                if (choice < modules.size() && choice > 0) {
                    String ModuleID = "'" + modules.get(choice - 1).getModuleID() + "'";
                    //Calls listOfStudents in timetable, passing the session and ModuleID as parameters
                    //Doesn't work yet
                    t.listOfStudents(ModuleID, se);
                } else if (choice > modules.size() || choice < 0) {
                    System.out.println("\nPlease select one of the choices.");
                    se.close();
                }
            } else {
                System.out.println("\nPlease enter an integer.");
                s.next();
            }

        } while (choice > modules.size() || choice < 1);
    }
}