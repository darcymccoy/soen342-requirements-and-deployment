package main.system;
import java.util.UUID;

public class Ticket {
    private UUID id;

    public Ticket() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid;
    }



}
