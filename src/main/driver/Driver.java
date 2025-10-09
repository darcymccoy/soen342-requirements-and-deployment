package main.driver;

import java.util.Scanner;

import main.system.*;

public class Driver {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayMainMenu();

        scanner.close();
        System.out.println("\nThe program has terminated.");
    }

    private static void displayMainMenu() {
        while (true) {
            String startCity = "";
            String endCity = "";
            try {
                System.out.print("Enter where you are departing: ");
                startCity = scanner.nextLine();
                System.out.print("Enter where you are travelling to: ");
                endCity = scanner.nextLine();

                ConnectionsCatalogue cc = new ConnectionsCatalogue(startCity, endCity);
                System.out.println(cc.toString());
                displayRouteOptionsMenu(cc);
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

    private static void displayRouteOptionsMenu(ConnectionsCatalogue connectionsCatalogue) {
        while (true) {
            try {
                System.out.print("1. Exit this menu.\n" +
                        "2. Sort by trip duration.\n" +
                        "3. Sort by second class rate.\n" +
                        "4. Sort by first class rate.\n" +
                        "Enter an integer to choose from the above options: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
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
