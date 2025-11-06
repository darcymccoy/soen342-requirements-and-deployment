package main.model;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Trip {
    private long tripId;
    private List<Reservation>  reservations = new ArrayList<Reservation>();
    private TrainConnection trainConnection;


    public Trip(TrainConnection trainConnection) {
        long timePart = System.currentTimeMillis();
        int randomPart = ThreadLocalRandom.current().nextInt(1000,9999);
        this.tripId = timePart + randomPart;
        this.trainConnection = trainConnection;

    }

    public Trip(long tripId, List<Reservation> reservations, TrainConnection trainConnection){
        this.tripId = tripId;
        this.reservations = reservations;
        this.trainConnection = trainConnection;
    }

    public void addReservation( String fname,String lname, int age, String pId)
    {
        checkId(pId);
        reservations.add(new Reservation(fname,lname,age,pId));
    }

    private void checkId(String pId) throws IllegalArgumentException
    {
        for (Reservation reservation : reservations)
        {
            if(reservation.getClient().getPassengerId().equals(pId))
            {
                throw new IllegalArgumentException();
            }
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public long getTripId() {
        return tripId;
    }

    public TrainConnection getTrainConnection(){
        return this.trainConnection;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n==============================\n");
        sb.append("Trip ID: ").append(tripId).append("\n");

        // Connection details
        if (trainConnection != null) {
            sb.append("Train Connection:\n");

            if (isValidRoute(trainConnection.getRoute1()))
                sb.append("  • Route 1: ").append(trainConnection.getRoute1()).append("\n");
            if (isValidRoute(trainConnection.getRoute2()))
                sb.append("  • Route 2: ").append(trainConnection.getRoute2()).append("\n");
            if (isValidRoute(trainConnection.getRoute3()))
                sb.append("  • Route 3: ").append(trainConnection.getRoute3()).append("\n");
        } else {
            sb.append("Train Connection: None\n");
        }

        // Passenger reservations
        sb.append("\nPassengers:\n");
        if (reservations != null && !reservations.isEmpty()) {
            for (Reservation r : reservations) {
                sb.append("  - ").append(r.getClient().toString()).append("\n");
            }
        } else {
            sb.append("  (No reservations)\n");
        }

        sb.append("==============================\n");
        return sb.toString();
    }

    /**
     * Checks if a route is valid enough to print.
     */
    private boolean isValidRoute(TrainRoute route) {
        if (route == null) return false;
        String id = route.getRouteID();
        return id != null && !id.isBlank();
    }
}


