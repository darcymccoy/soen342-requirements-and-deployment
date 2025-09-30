package main.driver;


import main.system.DatabaseReader;
import main.system.TrainConnection;

import java.util.ArrayList;

public class Driver {
	public static void main(String[] args) {

        DatabaseReader reader = new DatabaseReader();
        ArrayList<TrainConnection> conn = reader.PullDataFromDatabase();
        for(TrainConnection tc : conn){
            System.out.println(tc.toString());
        }
        System.out.println(conn.get(1).toString());
        System.out.println("\nThe program has terminated.");
    }


}
