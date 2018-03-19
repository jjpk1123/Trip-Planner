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
    public int version;
    public String type;
    public String title;
    public Option options;
    public ArrayList<Place> places;
    public ArrayList<Integer> distances;
    public String map;

    /**
     * Finds which starting place's path is shortest
     */
    private ArrayList<Integer> findShortestPath() {
        int size = this.places.size();
        ArrayList<Integer> shortestPath = new ArrayList<>();
        int shortestTotalDist = 9999999;

        for (int i=0; i < size; ++i) {
            ArrayList<Integer> distance = Distance.legDistances(this.places, this.options.distance);
            int sum = 0; // this iteration's total distance
            int curr = i;// starting Place
            for (int j=0; j < size; ++j) {
//                System.out.println("Dist between " + this.places.get(curr).name + " & "
//                        + this.places.get((curr + 1) % size).name + " is " + distance.get((curr + 1) % size));
                sum += distance.get(j);
                curr = (curr + 1) % size;// ++curr
            }
            if (sum < shortestTotalDist) {
                shortestTotalDist = sum;
                shortestPath = distance;
                startingPath(curr);
            }
//            System.out.println(); //adds a new line
        }
        return shortestPath;
    }

    /**
     * Reorders this.places as to start with te shortest path's starting Place
     * @param newStart = New starting Place
     */
    private void startingPath(int newStart) {
        System.out.println("Found a shorter path: " + this.places.get(newStart));
        for (int i = 0; i < newStart; ++i) {
            this.places.add(this.places.remove(0));
        }
    }

    /**
     * The top level method that does planning.
     */
    public void plan()  {
        //1. Plan the trip
        if (!this.options.optimization.equals("none")) {
            double optBreak = 1.0 / 2;
            double numOptimization = Double.parseDouble(this.options.optimization);
            //if (numOptimization < optBreak) {
            //    System.out.println("Without NearestNeighbor");
            //}
            if (numOptimization >= optBreak) { // && (numOptimization <= 2*opt)
                //System.out.println("With NearestNeighbor");
                this.places = Optimize.nearestNeighbor(this.places);
            }
        }

        //2. Draw the map of the plan
        this.map = new Svg(places).map;

        //3. Find distances
        this.distances = findShortestPath();
    }
}