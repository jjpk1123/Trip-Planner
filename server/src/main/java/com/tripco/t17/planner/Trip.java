package com.tripco.t17.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t17.server.HTTP;

import java.util.ArrayList;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import spark.Request;

/**
 * The Trip class supports TFFI so it can easily be converted to/from Json by Gson.
 */
public class Trip {
    // The variables in this class should reflect TFFI.
    public String type;
    public String title;
    public Option options;
    public ArrayList<Place> places;
    public ArrayList<Integer> distances;
    public String map;

    /**
     * The top level method that does planning.
     */
    public void plan()  {
        //1. Plan the trip
        if (!this.options.optimization.equals("none")) {
            double optBreak = 1.0 / 2;
            double numOptimization = Double.parseDouble(this.options.optimization);
            if (numOptimization < optBreak) {
                System.out.println("Without NearestNeighbor");
            } else if (numOptimization >= optBreak) { // && (numOptimization <= 2*opt)
                System.out.println("With NearestNeighbor");
                this.places = Optimize.nearestNeighbor(this.places);
            }
        }

        //2. Draw the map of the plan
        this.map = new Svg(places).map;

        //3. Find distances
        this.distances = Distance.legDistances(this.places, this.options.distance);
    }
}
