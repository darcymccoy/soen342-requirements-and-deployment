package main.system;

public class Reservation {

    private String Name;
    private int age;
    private String PassengerId;
    private Ticket ticket;


    public Reservation(String name, int age, String passengerId) {
        this.Name = name;
        this.age = age;
        this.PassengerId = passengerId;
        this.ticket = new Ticket();
    }



}
