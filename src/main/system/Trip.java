package main.system;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Trip {
    private UUID tripId;
    private List<Reservation>  reservations = new ArrayList<Reservation>();
    private TrainConnection trainConnection;


    public Trip(TrainConnection trainConnection) {
        this.tripId = UUID.randomUUID();
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

    public UUID getTripId() {
        return tripId;
    }
}


