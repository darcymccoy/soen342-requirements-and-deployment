package main.system;

public class RouteMaker {

    public RouteMaker(){

    }

    private String startCity;
    private String endCity;

    public RouteMaker(String startCity, String endCity) {
        this.startCity = startCity;
        this.endCity = endCity;
    }

    public boolean cityExists(String city){



        // TO DO: implement logic


        return true;
    }

    public String buildRoute(String startCity, String endCity) throws NoRouteException {

        // routes = findRoutes(startCity,endCity) -> SQL method hugo set it up
        // if routes != null then return routes.toString
        //else
        // findConnections(startCity,endCity);

        // TO DO: implement logic

        //return routes;
        return "Here are all the things we found!!";
    }

    public String findConnections(String startCity, String endCity)
    {
        //connections = [] -> tuple?
        //routesStart = findRoutes(startCity);
        //for routesStart , i++
        //  routeConnections = findRoutes(routesStart[i].getArrivalCity,endCity)
        //  if routeConnections ! = null
        //      for routeConnections, j++
        //      connections.add(routesStart[i],routeConnections[j])
        return "routes";
    }


}
