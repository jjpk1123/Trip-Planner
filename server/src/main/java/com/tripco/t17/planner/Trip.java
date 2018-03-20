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
 * Equivalent to Application.js
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
     * Finds which starting place's path is shortest.
     *
     */
    private void findShortestPath() {
        int size = this.places.size();
        ArrayList<Integer> shortestPathDists = new ArrayList<>();//list of distances of the shortest path
        ArrayList<Place> shortestPathPlaces = new ArrayList<>(); //list of places of the shortest path
        int shortestPathTotalDist = retrieveSum(this.distances, size);

        for (int i=0; i < size; ++i) {
            ArrayList<Place> iterPlaces = Optimize.nextStart(i, this.places, size);//startingPlace++
            ArrayList<Integer> iterDists = Distance.legDistances(iterPlaces, this.options.distance);

            int sum = retrieveSum(iterDists, size); // this iteration's total distance
            if (sum < shortestPathTotalDist) {
                shortestPathTotalDist = sum;
                shortestPathDists = iterDists;
                shortestPathPlaces = foundShorterPath(i, iterPlaces);
            }
            System.out.println(); //adds a new line
        }
        this.distances = shortestPathDists;
        this.places = shortestPathPlaces;
    }

    /**
     * Computes sum of distances
     */
    public int retrieveSum(ArrayList<Integer> dists, int size) {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            sum += dists.get(i);
        }
        return sum;
    }

    /**
     * Reorders this.places as to start with the shortest path's starting Place
     * @param newStart = New starting Place
     * @param iterPlaces = the current iteration's list of Places in order
     */
    private ArrayList<Place> foundShorterPath(int newStart, ArrayList<Place> iterPlaces) {
        System.out.println("Found a shorter path starting with " + this.places.get(newStart).name);
        for (int i = 0; i < newStart; ++i) {
            System.out.println("Moving " + this.places.get(0).name + " to the end of the list");
            iterPlaces.add(iterPlaces.remove(0));
        }
        return iterPlaces;
    }

    /**
     * The top level method that does planning.
     */
    public void plan()  {
        //1. Plan the trip
        if (!this.options.optimization.equals("none")) {
            //if (!Option.optimizeCheck(this.options.optimization)) {
            //    System.out.println("Without NearestNeighbor");
            //}
            if (Option.optimizeCheck(this.options.optimization)) { // && (numOptimization <= 2*opt)
                //System.out.println("With NearestNeighbor");
                this.places = Optimize.nearestNeighbor(this.places);
            }
        }

        //2. Draw the map of the plan
        this.map = new Svg(places).map;

        //3. Find distances
        this.distances = Distance.legDistances(this.places, this.options.distance);
        if (Option.optimizeCheck(this.options.optimization)) {
            findShortestPath();
        }
    }
}