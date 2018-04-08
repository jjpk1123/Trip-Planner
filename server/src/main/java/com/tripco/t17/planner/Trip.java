package com.tripco.t17.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t17.server.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Math;
import java.util.ArrayList;

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
     * The top level method that does planning.
     */
    public void plan()  {
        //1. Plan the trip
        if (Option.optimizeCheck(this.options.optimization)) { //greater than ("none" || "0.0")
            double optBreak = 1.0 / Config.getOptimizationLevels(); // (1) + 1
            double numOptimization = Double.parseDouble(this.options.optimization);
            this.places = Optimize.optimize(this.places, numOptimization);

//            if ((numOptimization >= optBreak) && (numOptimization < 2*optBreak)){
//                System.out.println("Computing NearestNeighbor");
//                Place start = this.places.get(0);
//                this.places = Optimize.nearestNeighbor(this.places);
//                this.places = Optimize.changeStart(this.places, this.places.indexOf(start));
//            }
//            else if ((numOptimization >= 2*optBreak) && (numOptimization <= 3*optBreak)) {
//                // use ^^this^^ one for 2 opt, but save the bottom one for when we get to 3-opt
//                this.places = Optimize.optimize(this.places, numOptimization);
//            }
            //else if ((numOptimization >= 2*optBreak) && (numOptimization < 3*optBreak)) {
            //  System.out.println("Computing 2-opt");
            //  this.places = Optimize.twoOpt(this.places)
            //}
            //else if ((numOptimization >= 3*optBreak) && (numOptimization <= 4*optBreak)) {
            //  System.out.println("Computing 3-opt");
            //  this.places = Optimize.threeOpt(this.places)
            //}
        }

        //2. Draw the map of the plan
        this.map = new Svg(places).map;

        //3. Find distances
        this.distances = Distance.legDistances(this.places,
                this.options.distance, this.options.userRadius);
    }
}
