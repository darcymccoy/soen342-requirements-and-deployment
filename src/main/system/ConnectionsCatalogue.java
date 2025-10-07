package main.system;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ConnectionsCatalogue {

    public ConnectionsCatalogue(){

    }
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("H:mm");

    private String startCity;
    private String endCity;
    private  List<TrainRoute>  routes = new ArrayList<TrainRoute>();
    private DatabaseReader reader = new DatabaseReader();

    public ConnectionsCatalogue(String startCity, String endCity) {
        this.startCity = startCity;
        this.endCity = endCity;
        ArrayList<TrainRoute> cities = reader.listAllCities();


    }

    public boolean cityExists(String city){





        return true;
    }

    public String buildRoute() throws NoRouteException {

        routes = reader.findDepartureArrivalPair(startCity, endCity);
        if(!routes.isEmpty()) {
            for(TrainRoute route : routes){
                LocalTime t1 = LocalTime.parse(route.getDepartureTime(), FMT);
                LocalTime t2 = LocalTime.parse(route.getArrivalTime(), FMT);
                long unit = ChronoUnit.MINUTES.between(t1, t2);
                System.out.println(route.toString() + " Trip Duration: " + unit+"mins");

            }
        }
        else{

            List<TrainRoute[]> connections = findConnections();
            if(connections != null)
            {
                System.out.println("Our connections are:");
                for (TrainRoute[] connection : connections)
                {
                    System.out.println("Route 1: "+connection[0].toString()+" to \nRoute 2:"+connection[1].toString()+"\n");
                }
            }
            return "Here are all the connections we found!!";
        }
        return "Here are all the things we found!!";
    }

    public List<TrainRoute[]> findConnections()
    {

        List<TrainRoute[]>  connections = new ArrayList<TrainRoute[]>();
        List<TrainRoute> routePart1 = reader.findDepartures(startCity);
        for (int i = 0; i < routePart1.size();i++ ) {

            List<TrainRoute> routePart2temp = reader.findDepartureArrivalPair(routePart1.get(i).getArrivalCity(), endCity);
            for (int j = 0; j < routePart2temp.size();j++ )
            {

                TrainRoute[] temp = {routePart1.get(i), routePart2temp.get(j)};
                connections.add(temp);
            }

        }
        if (connections.isEmpty())
        {
            findConnections2();
        }
        return connections;
    }

    public boolean DayOperation(String a, String b)
    {



        return false;
    }



    public List<TrainRoute[]>  findConnections2()
    {
        return null;
    }

    public String ToString()
    {
        return "";
    }

}
