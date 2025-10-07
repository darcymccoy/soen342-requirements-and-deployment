package main.system;

public class TrainRoutes {
    private String routeID;
    private String departureCity;
    private String arrivalCity;
    private String departureTime;
    private String arrivalTime;
    private String trainType;
    private String daysOfOperation;
    private String firstClassTicketRate;
    private String secondClassTicketRate;

    public TrainRoutes(String routeID, String departureCity, String arrivalCity, String departureTime, String arrivalTime, String trainType, String daysOfOperation, String firstClassTicketRate, String secondClassTicketRate){
        this.routeID = routeID;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.trainType = trainType;
        this.daysOfOperation = daysOfOperation;
        this.firstClassTicketRate = firstClassTicketRate;
        this.secondClassTicketRate = secondClassTicketRate;
    }

    public String getRouteID() {
        return routeID;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getTrainType() {
        return trainType;
    }

    public String getDaysOfOperation() {
        return daysOfOperation;
    }

    public String getFirstClassTicketRate() {
        return firstClassTicketRate;
    }

    public String getSecondClassTicketRate() {
        return secondClassTicketRate;
    }

    @Override
    public String toString(){
        return "Route ID: " + getRouteID() + " | Departure City: " + getDepartureCity() + " | Arrival City: " + getArrivalCity() + " | Departure Time: " + getDepartureTime() + " | Arrival Time: " + getArrivalTime() + " | Train Type: " + getTrainType() + " | Days of operation: " + getDaysOfOperation() + " | First class ticket rate: " + getFirstClassTicketRate() + " | Second class ticket rate: " + getSecondClassTicketRate();
    }
}
