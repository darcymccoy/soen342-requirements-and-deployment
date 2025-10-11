package main.system;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ConnectionsCatalogue {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("H:mm");

    private String startCity;
    private String endCity;
    private List<TrainRoute> routes = new ArrayList<TrainRoute>();
    private DatabaseReader reader = new DatabaseReader();
    private List<TrainConnection> catalogue = new ArrayList<>();


    public ConnectionsCatalogue(String startCity, String endCity, String[] arr) throws NoRouteException {
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
        }
        else{


            buildRoute(arr);
        }

    }


    public void buildRoute(String[] arr) throws NoRouteException {
        reader.resetArrs();
        for(int i = 0; i < 6; i++){
            if(arr[i] != null){
                switch(i){
                    case 0:
                        reader.filterByDepTime(arr[i]);
                        break;
                    case 1:
                        reader.filterByArrTime(arr[i]);
                        break;
                    case 2:
                        reader.filterByTrainType(arr[i]);
                        break;
                    case 3:
                        reader.filterByDOO(arr[i]);
                        break;
                    case 4:
                        reader.filterByFirstClass(arr[i]);
                        break;
                    case 5:
                        reader.filterBySecondClass(arr[i]);
                        break;
                    default:
                        break;
                }
            }
        }
        routes = reader.findDepartureArrivalPair(startCity, endCity);
        reader.resetArrs();
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
            boolean valid = false;
            List<TrainRoute[]> connections = findConnections();
            if (connections != null) {
                for (TrainRoute[] connection : connections) {
                    TrainRoute route1 = connection[0];
                    TrainRoute  route2 = connection[1];

                    if (timeOperation(route1, route2)>0)
                    {
                        if(dayOperation(route1.getDaysOfOperation(),route2.getDaysOfOperation()))
                        {
                            TrainConnection tc = new TrainConnection(route1,route2, timeOperation(route1, route2));
                            catalogue.add(tc);
                            valid = true;
                        }

                    }

                }
              if (valid) {return;}
            }
        }
        if (!findConnections2()){throw new NoRouteException();};

    }

    private List<TrainRoute[]> findConnections() {

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


    private long timeOperation(TrainRoute a, TrainRoute b) {

        LocalTime a1 = LocalTime.parse(a.getDepartureTime(), FMT);
        LocalTime a2 = LocalTime.parse(a.getArrivalTime(), FMT);
        LocalTime b1 = LocalTime.parse(b.getDepartureTime(), FMT);
        LocalTime b2 = LocalTime.parse(b.getArrivalTime(), FMT);

        if (ChronoUnit.MINUTES.between(a2, b1) > 0) {
            return ChronoUnit.MINUTES.between(a1, b2);
        }

        return -1;
    }


    private boolean dayOperation(String a, String b) {
        if (a.equals("Daily") || b.equals("Daily")) {
            return true;
        }

        return switch (a) {
            case ("Fri-Sun") -> !b.equals("Tue,Thu");
            case ("Mon,Wed,Fri") -> !b.equals("Tue,Thu") && !b.equals("Sat-Sun");
            case ("Tue,Thu") -> b.equals("Mon-Fri");
            case ("Sat-Sun") -> b.equals("Fri-Sun");
            case ("Mon-Fri") -> !b.equals("Sat-Sun");
            default -> false;
        };

    }

    private boolean findConnections2() {
        boolean valid  = false;
        List<TrainRoute> routePart1 = reader.findDepartures(startCity);
        List<TrainRoute> routePart2 = reader.findArrivals(endCity);

        for (TrainRoute routeStart : routePart1) {
            String city = routeStart.getArrivalCity();
            for (TrainRoute routeEnd : routePart2) {
                List<TrainRoute> temp = reader.findDepartureArrivalPair(city, routeEnd.getDepartureCity());

                for (TrainRoute route : temp) {

                    if( (timeOperation(routeStart, route) > 0) && (timeOperation(route, routeEnd) > 0)) {
                        if (dayOperation(routeStart.getDaysOfOperation(), route.getDaysOfOperation()) && dayOperation(route.getDaysOfOperation(), routeEnd.getDaysOfOperation())) {
                            valid = true;
                            catalogue.add(new TrainConnection(routeStart, route, routeEnd, timeOperation(routeStart, route) + timeOperation(route, routeEnd)));

                        }
                    }
                }
            }
        }
        return valid;

    }



    public void sortByDuration() {
        catalogue.sort(Comparator.comparingLong(TrainConnection::getDuration));
    }

    public void sortBySecondClassRate() {
        catalogue.sort(Comparator.comparingInt(TrainConnection::getSecondCLassRate));
    }

    public void sortByFirstClassRate() {
        catalogue.sort(Comparator.comparingInt(TrainConnection::getFirstCLassRate));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < catalogue.size(); i++) {
            sb.append("Option " + (i + 1))
                    .append(": \n")
                    .append(catalogue.get(i))
                    .append(System.lineSeparator() + "\n");
        }
        return sb.toString();

    }

}
