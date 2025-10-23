package main.system;

public class Reservation {

    private Client client;
    private Ticket ticket;


    public Reservation(String firstname,String lastname, int age, String passengerId) {
        this.client = new Client(firstname,lastname,age,passengerId);
        this.ticket = new Ticket();
    }

    public Client getClient() {
        return client;
    }
}
