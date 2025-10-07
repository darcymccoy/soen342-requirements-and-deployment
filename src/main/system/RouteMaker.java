package main.system;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class RouteMaker {

    public RouteMaker(){

    }

    private String startCity;
    private String endCity;
    private  List<TrainRoute>  routes = new ArrayList<TrainRoute>();
    private DatabaseReader reader = new DatabaseReader();

    public RouteMaker(String startCity, String endCity) {
        this.startCity = startCity;
        this.endCity = endCity;
    }

    public boolean cityExists(String city){



        // TO DO: implement logic


        return true;
    }

    public String buildRoute() throws NoRouteException {

        // if routes != null then return routes.toString
        //else
        // findConnections();

        // TO DO: implement logic

        //return routes;
        return "Here are all the things we found!!";
    }

    public String findConnections()
    {
        //connections;
        //routesStart = findRoutes(startCity);
        //for routesStart , i++
        //  routeConnections = findRoutes(routesStart[i].getArrivalCity,endCity)
        //  if routeConnections ! = null
        //      for routeConnections, j++
        //      connections.add(routesStart[i],routeConnections[j])
        return "routes";
    }


}
