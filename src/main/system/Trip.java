package main.system;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Trip {
    private UUID tripId;
    private List<Reservation>  reservations = new ArrayList<Reservation>();
    private TrainConnection trainConnection;
    private String BookerName;
    private String BookerId;


    public Trip(TrainConnection trainConnection, String fName, String lName, int age, String Pid) {
        this.tripId = UUID.randomUUID();
        this.trainConnection = trainConnection;
        this.BookerName = lName;
        this.BookerId = Pid;
        reservations.add(new Reservation(fName,lName,age,Pid));
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
            if(reservation.getPassengerId().equals(pId))
            {
                throw new IllegalArgumentException();
            }
        }
    }

}


