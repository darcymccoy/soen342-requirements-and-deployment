package main.model;

public class Client {

    private String firstname;
    private String lastname;
    private int age;
    private String passengerId;


    public Client(String firstname, String lastname, int age, String passengerId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.passengerId = passengerId;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public int getAge(){
        return this.age;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassengerId() {
        return passengerId;
    }
}
