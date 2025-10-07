package main.system;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ConnectionsCatalogue {

    public ConnectionsCatalogue() {

    }

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("H:mm");

    private String startCity;
    private String endCity;
    private List<TrainRoute> routes = new ArrayList<TrainRoute>();
    private DatabaseReader reader = new DatabaseReader();
    private List<TrainConnection> catalogue = new ArrayList<>();


    public ConnectionsCatalogue(String startCity, String endCity) throws NoRouteException {
        this.startCity = startCity;
        this.endCity = endCity;
        int bothCitiesValid = 0;
        ArrayList<String> cities = reader.listAllCities();
        for (String city : cities) {
            if (city.equals(startCity) || city.equals(endCity)) {
                bothCitiesValid += 1;
            }
            if (bothCitiesValid == 2) {
                break;
            }
        }

        if (bothCitiesValid < 2) {
            NoRouteException e = new NoRouteException();
            throw e;
        } else buildRoute();

    }


    public void buildRoute() throws NoRouteException {

        routes = reader.findDepartureArrivalPair(startCity, endCity);
        if (!routes.isEmpty()) {
            for (TrainRoute route : routes) {
                LocalTime t1 = LocalTime.parse(route.getDepartureTime(), FMT);
                LocalTime t2 = LocalTime.parse(route.getArrivalTime(), FMT);
                long unit = ChronoUnit.MINUTES.between(t1, t2);
                TrainConnection tc = new TrainConnection(route, unit);
                catalogue.add(tc);
            }
            return;
        } else {
            List<TrainRoute[]> connections = findConnections();
            if (connections != null) {
                for (TrainRoute[] connection : connections) {
                    TrainConnection tc = new TrainConnection(connection[0], connection[1], 10);
                    catalogue.add(tc);
                }
                return;
            }
        }
        findConnections2();

    }

    public List<TrainRoute[]> findConnections() {

        List<TrainRoute[]> connections = new ArrayList<TrainRoute[]>();
        List<TrainRoute> routePart1 = reader.findDepartures(startCity);
        for (int i = 0; i < routePart1.size(); i++) {

            List<TrainRoute> routePart2temp = reader.findDepartureArrivalPair(routePart1.get(i).getArrivalCity(), endCity);
            for (int j = 0; j < routePart2temp.size(); j++) {

                TrainRoute[] temp = {routePart1.get(i), routePart2temp.get(j)};
                connections.add(temp);
            }

        }

        return connections;
    }

    public boolean DayOperation(String a, String b) {


        return false;
    }


    public List<TrainRoute[]> findConnections2() {
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < catalogue.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(catalogue.get(i)) // relies on TrainConnection.toString()
                    .append(System.lineSeparator());
        }
        return sb.toString();

    }

}
