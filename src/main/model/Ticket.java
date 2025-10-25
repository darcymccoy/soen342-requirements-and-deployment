package main.model;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Ticket {
    private long ticket_id;

    public Ticket() {
        long timePart = System.currentTimeMillis();
        int randomPart = ThreadLocalRandom.current().nextInt(1000,9999);
        ticket_id = timePart + randomPart;
    }
    public long getId(){
        return ticket_id;
    }



}
