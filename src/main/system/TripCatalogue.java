package main.system;

import java.util.ArrayList;
import java.util.List;

public class TripCatalogue {

    private static List<Trip> trips =  new ArrayList<Trip>();

    public static List<Trip> findTrips(String name, String Pid)
    {
        ArrayList<Trip> validTrips = new ArrayList<Trip>();

        for (Trip t : trips)
        {
           for (Reservation r : t.getReservations())
           {
               if (r.getClient().getPassengerId().equals(Pid) && r.getClient().getLastname().equals(name))
               {
                   validTrips.add(t);
               }
           }
        }
        return validTrips;
    }

    public static void addTrip(Trip trip) {
        trips.add(trip);
    }

}
