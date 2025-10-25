package main.model;

public class TrainRoute {
    private String routeID;
    private String departureCity;
    private String arrivalCity;
    private String departureTime;
    private String arrivalTime;
    private String trainType;
    private String daysOfOperation;
    private int firstClassTicketRate;
    private int secondClassTicketRate;

    public TrainRoute(String routeID, String departureCity, String arrivalCity, String departureTime, String arrivalTime, String trainType, String daysOfOperation, int firstClassTicketRate, int secondClassTicketRate){
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

    public int getFirstClassTicketRate() {
        return firstClassTicketRate;
    }

    public int getSecondClassTicketRate() {
        return secondClassTicketRate;
    }

    @Override
    public String toString() {
        String safeRouteId = "(no ID)";
        try {
            String id = getRouteID();
            if (id != null && id.length() > 1) {
                safeRouteId = String.valueOf(SimpleXorObfuscator.encodeNumber(
                        Integer.parseInt(id.substring(1))
                ));
            }
        } catch (Exception e) {
            // Defensive: prevents crash if parsing or encoding fails
            safeRouteId = "(invalid ID)";
        }

        return "Route ID: " + safeRouteId
                + " | Departure City: " + safe(getDepartureCity())
                + " | Arrival City: " + safe(getArrivalCity())
                + " | Departure Time: " + safe(getDepartureTime())
                + " | Arrival Time: " + safe(getArrivalTime())
                + " | Train Type: " + safe(getTrainType())
                + " | Days of operation: " + safe(getDaysOfOperation())
                + " | First class ticket rate: " + getFirstClassTicketRate()
                + " | Second class ticket rate: " + getSecondClassTicketRate();
    }

    // Helper method to safely handle null strings
    private String safe(String value) {
        return value != null ? value : "(unknown)";
    }
}
