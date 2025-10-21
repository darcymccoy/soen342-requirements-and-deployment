package main.system;

public class Reservation {

     private String firstname;
    private String lastname;
    private int age;
    private String PassengerId;
    private Ticket ticket;


    public Reservation(String firstname,String lastname, int age, String passengerId) {
        this.firstname = firstname;
        this.lastname = lastname;

        this.age = age;
        this.PassengerId = passengerId;
        this.ticket = new Ticket();
    }

    public String  getPassengerId() {
        return PassengerId;
    }
}
