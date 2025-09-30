package main.driver;

import java.util.Scanner;

import main.system.NoRouteException;
import main.system.RouteMaker;

public class Driver {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RouteMaker routeMaker = new RouteMaker();

    public static void main(String[] args) {
        mainMenu();

        System.out.println("\nThe program has terminated.");

        scanner.close();
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
                if (routeMaker.cityExists(startCity) && routeMaker.cityExists(endCity)) {
                    System.out.print(routeMaker.buildRoute(startCity, endCity));
                    routeOptionsMenu(startCity, endCity);
                } else {
                    System.out.println("One or both of the city names were not inputted correctly");
                }
            } catch (RuntimeException e) {
                scanner.nextLine();
                System.out.println("This isn't an allowed input. Try again.");
            } catch (NoRouteException e) {
                System.out.print("There are no connections between these 2 cities (with maximum 2 stops)");
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
                System.out.print("\n1. Exit this menu.\n" +
                        "2. Sort by trip duration.\n" +
                        "3. Sort by price.\n" +
                        "Enter an integer to choose from the above options: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    return;
                } else if (choice == 2) {

                } else if (choice == 3) {

                } else {
                    System.out.println("This wasn't one of the integer choices. Try again.");
                }
            } catch (NumberFormatException e) {
                scanner.nextLine();
                System.out.println("This isn't an allowed input. Try again.");
            }
        }
    }
}
