package main.driver;

import java.util.ArrayList;
import java.util.Scanner;

import main.system.*;

public class Driver {
    private static final Scanner scanner = new Scanner(System.in);
    private static DatabaseReader dbReader = new DatabaseReader();
    private static ArrayList<TrainRoute> routes;

    public static void main(String[] args) {
        mainMenu();

        scanner.close();
        System.out.println("\nThe program has terminated.");
    }

    private static void mainMenu() {
        while (true) {
            String startCity = "";
            String endCity = "";
            try {
                System.out.print("Enter where you are departing: ");
                startCity = scanner.nextLine();
                System.out.print("Enter where you are travelling to: ");
                endCity = scanner.nextLine();

                ConnectionsCatalogue tc = new ConnectionsCatalogue(startCity,endCity);

                    routeOptionsMenu(startCity, endCity);

            } catch (NoRouteException e) {
                System.out.print("\nThere are no connections between these 2 cities (with maximum 2 stops)");
            }
            try {
                System.out.print("Enter 1 to exit the program (or <enter> to continue): ");
                int exitInput = Integer.parseInt(scanner.nextLine());
                if (exitInput == 1) {
                    return;
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    private static void routeOptionsMenu(String startCity, String endCity) {
        while (true) {
            try {
                System.out.print("1. Exit this menu.\n" +
                        "2. Sort by trip duration.\n" +
                        "3. Sort by price.\n" +
                        "Enter an integer to choose from the above options: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    return;
                } else if (choice == 2) {
                    sortByDuration();
                } else if (choice == 3) {
                    sortByPrice();
                } else {
                    System.out.println("\nThis wasn't one of the integer choices. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nThis isn't an allowed input. Try again.");
            }
        }
    }

    private static void sortByDuration() {
        // TODO: Implement sort
        System.out.println("\nHere are the routes sorted by duration!");
    }

    private static void sortByPrice() {
        // TODO: Implement sort
        System.out.println("\nHere are the routes sorted by price!");
    }
}
