package main.driver;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.system.*;

public class Driver {
    private static final Scanner scanner = new Scanner(System.in);
    private static String[] paramArr = {null,null,null,null,null,null};
    public static void main(String[] args) {
        displayMainMenu();

        scanner.close();
        System.out.println("\nThe program has terminated.");
    }

    private static void displayMainMenu() {
        while (true) {
            try {
                System.out.print("""
                        1. Exit the program.
                        2. Book a Trip.
                        3. View Trips.
                        Enter an integer to choose from the above options:\s""");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    return;
                } else if (choice == 2) {
                    displayConnectionsMenu();
                } else if (choice == 3) {
                    displayTripsMenu();
                } else {
                    System.out.println("\nThis wasn't one of the integer choices. Try again.");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("\nThis isn't an allowed input. Try again.");
            }
        }
    }

    private static void displayTripsMenu() {
        String lastName = "";
        String id = "";
        System.out.print("Enter your last name: ");
        lastName = scanner.nextLine();
        System.out.print("Enter your id: ");
        id = scanner.nextLine();
        List<Trip> trips = TripCatalogue.findTrips(lastName, id);
        for (Trip trip : trips) {
            System.out.println(trip);
        }
    }

    private static void displayConnectionsMenu() {
        while (true) {
            String startCity = "";
            String endCity = "";
            try {
                System.out.print("Enter where you are departing: ");
                startCity = scanner.nextLine();
                System.out.print("Enter where you are travelling to: ");
                endCity = scanner.nextLine();

                String choiceParameter = "";
                while(!choiceParameter.equalsIgnoreCase("no")){
                    choiceParameter = displayParameterOptions("");
                    if(choiceParameter.equalsIgnoreCase("no")){
                        break;
                    }
                    else if (choiceParameter.equals("-1")){
                        continue;
                    }

                }

                ConnectionsCatalogue connections = new ConnectionsCatalogue(startCity, endCity, paramArr);
                System.out.println(connections);
                displayRouteOptionsMenu(connections);
                System.out.print("\nEnter the corresponding route number to choose your connection: ");
                int connectionIndex = Integer.parseInt(scanner.nextLine());
                bookTripMenu(connections.getTrainConnection(connectionIndex - 1));
            } catch (NoRouteException e) {
                System.out.print("\nThere are no connections between these 2 cities (with maximum 2 stops)");
            }
            try {
                System.out.print("Enter 1 to exit the connections menu (or <enter> to continue): ");
                int exitInput = Integer.parseInt(scanner.nextLine());
                if (exitInput == 1) {
                    return;
                }
            } catch (NumberFormatException ignored) {
            }
        }
    }

    private static void bookTripMenu(TrainConnection connection) {
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter your passenger id: ");
        String passengerID = scanner.nextLine();
        Trip trip = new Trip(connection, firstName, lastName, age, passengerID);
        while(true) {
            try {
                System.out.print("""
                        \n1. Continue.
                        2. Add another reservation to this trip.
                        Enter an integer to choose from the above options:\s""");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    break;
                } else {
                    System.out.print("Enter the additional passenger first name: ");
                    String extraFirstName = scanner.nextLine();
                    System.out.print("Enter the additional passenger last name: ");
                    String extraLastName = scanner.nextLine();
                    System.out.print("Enter the additional passenger age: ");
                    int extraAge = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter the additional passenger id: ");
                    String extraPassengerID = scanner.nextLine();
                    trip.addReservation(extraFirstName, extraLastName, extraAge, extraPassengerID);
                }
            } catch (NumberFormatException e) {
                System.out.println("\nThis isn't an allowed input. Try again.");
            }
        }
        TripCatalogue.addTrip(trip);
    }

    private static String displayParameterOptions(String choice){
        try{
            System.out.println("Are there any parameters you would like to specify in the following list? \n(Type NO or type a number from the list)");
            System.out.println("""
            1. Departure Time
            2. Arrival Time
            3. Train Type
            4. Days of Operation
            5. First class ticket rate
            6. Second class ticket rate
            """);
            choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("no"))
                return "no";
            int paramChoice = Integer.parseInt(choice);
            if(paramChoice > 6 || paramChoice < 0){
                throw new NumberFormatException(choice);
            }
            while(true){
                String time;
                String regex = "^\\d{2}:\\d{2}$";
                switch(paramChoice){
                    case 1:
                        System.out.println("Please type in a time you would like the train to leave at (in 24hr time following format 00:00):");
                        time = scanner.nextLine();
                        if(time.equalsIgnoreCase("back"))
                            return "-1";
                        if (!time.matches(regex)){
                            System.out.println("Time entered not valid, please try again or type BACK to go back.");
                            break;
                        }
                        paramArr[0] = time;
                        return "";
                    case 2:
                        System.out.println("Please type in a time you would like the train to arrive at (in 24hr time following format 00:00):");
                        time = scanner.nextLine();
                        if(time.equalsIgnoreCase("back"))
                            return "-1";
                        if (!time.matches(regex)){
                            System.out.println("Time entered not valid, please try again or type BACK to go back.");
                            break;
                        }
                        paramArr[1] = time;
                        return time;
                    case 3:
                        System.out.println("Please select a train type from the following list:");
                        System.out.println("""
                                RJX
                                ICE
                                InterCity
                                Frecciarossa
                                RegioExpress
                                EuroCity
                                TGV
                                Italo
                                RE
                                Nightjet
                                Intercit?s
                                Thalys
                                Eurostar
                                TER
                                IC
                                AVE
                                Railijet
                                """);
                        paramArr[2] = scanner.nextLine();
                        return "";
                    case 4:
                        System.out.println("Please select days of operation from the following list:");
                        System.out.println("""
                                Fri-Sun
                                Daily
                                Mon,Wed,Fri
                                Tue,Thu
                                Sat-Sun
                                Mon-Fri
                                """);
                        paramArr[3] = scanner.nextLine();
                        return "";
                    case 5:
                        System.out.println("Please type a rate you would like for First Class Tickets");
                        paramArr[4] = scanner.nextLine();
                        return "";
                    case 6:
                        System.out.println("Please type a rate you would like for Second Class Tickets");
                        paramArr[5] = scanner.nextLine();
                        return "";

                }
            }

        } catch (NumberFormatException nfe){
            System.out.println("The input was not as expected! Please try again");
            return "-1";
        }
    }

    private static void displayRouteOptionsMenu(ConnectionsCatalogue connectionsCatalogue) {
        while (true) {
            try {
                System.out.print("1. Exit this menu to select a connection.\n" +
                        "2. Sort by trip duration.\n" +
                        "3. Sort by second class rate.\n" +
                        "4. Sort by first class rate.\n" +
                        "Enter an integer to choose from the above options: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    paramArr = new String[]{null,null,null,null,null,null};
                    return;
                } else if (choice == 2) {
                    connectionsCatalogue.sortByDuration();
                    System.out.println(connectionsCatalogue);
                } else if (choice == 3) {
                    connectionsCatalogue.sortBySecondClassRate();
                    System.out.println(connectionsCatalogue);
                } else if (choice == 4) {
                    connectionsCatalogue.sortByFirstClassRate();
                    System.out.println(connectionsCatalogue);
                } else {
                    System.out.println("\nThis wasn't one of the integer choices. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nThis isn't an allowed input. Try again.");
            }
        }
    }
}
