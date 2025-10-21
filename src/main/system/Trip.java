package main.system;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Trip {
    private UUID tripId;
    private List<Reservation>  reservations = new ArrayList<Reservation>();

    public Trip() {
        this.tripId = UUID.randomUUID();

    }

}
