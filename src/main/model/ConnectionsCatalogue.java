package main.model;

import main.dao.TrainRouteDAO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ConnectionsCatalogue {


    private String startCity;
    private String endCity;

    private List<TrainRoute> routes = new ArrayList<TrainRoute>();
    TrainRouteDAO routeDAO = new TrainRouteDAO();
    private List<TrainConnection> catalogue = new ArrayList<>();
    FilterCriteria filterCriteria;


    public ConnectionsCatalogue(String startCity, String endCity, FilterCriteria filterCriteria) throws NoRouteException {
        this.startCity = startCity;
        this.endCity = endCity;
        this.filterCriteria = filterCriteria;
        int bothCitiesValid = 0;
        ArrayList<String> cities = routeDAO.listAllCities();
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
            buildRoute();
        }

    }


    public void buildRoute() throws NoRouteException {
        routes = routeDAO.findDepartureArrivalPair(startCity, endCity, filterCriteria.getFilters());
        if (!routes.isEmpty()) {
            for (TrainRoute route : routes) {
                ScheduleMaker scheduleMaker = new ScheduleMaker(route);
                TrainConnection tc = new TrainConnection(route, scheduleMaker.getDuration());
                catalogue.add(tc);
            }
            return;
        } else {
            boolean valid = false;
            List<TrainRoute[]> connections = findConnections();


                for (TrainRoute[] connection : connections) {
                    ScheduleMaker scheduleMaker = new ScheduleMaker(connection[0], connection[1]);

                   if ( scheduleMaker.getValid())
                   {
                       TrainConnection tc = new TrainConnection(connection[0],connection[1], scheduleMaker.getDuration());
                       catalogue.add(tc);
                       valid = true;
                   }

                }
              if (valid) {return;}
        }
        if (!findConnections2()){throw new NoRouteException();};

    }

    private List<TrainRoute[]> findConnections() {

        List<TrainRoute[]> connections = new ArrayList<TrainRoute[]>();
        List<TrainRoute> routePart1 = routeDAO.findDepartures(startCity);
        for (int i = 0; i < routePart1.size(); i++) {

            List<TrainRoute> routePart2temp = routeDAO.findDepartureArrivalPair(routePart1.get(i).getArrivalCity(), endCity, filterCriteria.getFilters());
            for (int j = 0; j < routePart2temp.size(); j++) {
                TrainRoute[] temp = {routePart1.get(i), routePart2temp.get(j)};
                connections.add(temp);
            }

        }
        return connections;
    }




    private boolean findConnections2() {
        boolean valid  = false;
        List<TrainRoute> routePart1 = routeDAO.findDepartures(startCity);
        List<TrainRoute> routePart2 = routeDAO.findArrivals(endCity);

        for (TrainRoute routeStart : routePart1) {
            String city = routeStart.getArrivalCity();
            for (TrainRoute routeEnd : routePart2) {
                List<TrainRoute> temp = routeDAO.findDepartureArrivalPair(city, routeEnd.getDepartureCity(), filterCriteria.getFilters());

                for (TrainRoute route : temp) {
                    ScheduleMaker scheduleMaker = new ScheduleMaker(routeStart, route,route,routeEnd);

                    if (scheduleMaker.getValid())
                    {
                        valid = true;
                        catalogue.add(new TrainConnection(routeStart, route, routeEnd, scheduleMaker.getDuration()));

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

    public TrainConnection getTrainConnection(int index) {
        return catalogue.get(index);
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
