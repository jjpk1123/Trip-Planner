package com.tripco.t17.planner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Query class supports TFFI so it can easily be converted to/from Json by Gson.
 */
public class Query {
    // The variables in this class should reflect TFFI.
    public String type;
    public String query;
    public ArrayList<Place> places;

    /**
     * The top level method that does searching.
     */
    public void searchDatabase() {
        //This will do something one day :)
        System.out.println("YOU MADE IT TO SEARCHDATABASE");

        //At some point we'll receive places, so then we'll have to update our places array!
        //Hard coded example to make sure it goes through
        Place a = new Place();
        a.name = "a";
        a.latitude = "123";
        a.longitude = "321";
        a.id = "ay";

        places.add(a);
    }
}
