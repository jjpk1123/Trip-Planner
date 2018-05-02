package com.tripco.t17.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t17.server.HTTP;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import spark.Request;



/**
 * Straight-forward class. Gets called for REST API "/config"
 */
public class Config {
    // The variables in this class should reflect TFFI.
    public String type;
    public int version;
    public ArrayList<Filter> filters;
    public String[] maps;
    public int optimization;
    public Dictionary[] optimizations;
    public String[] units;

    /**
     * Called from REST API. Updates values of the server's configuration file
     */
    public void retrieveValues() {
        this.type = "config";
        this.version = 3;
        createTheFilterDictionary();
        this.maps = new String[]{"svg"};//{"svg", "kml"};
        this.optimization = 3;
        createTheOptDictionary();
        this.units = new String[]{"kilometers", "miles", "nautical miles", "user defined"};
    }

    /**
     * Returns many Optimization levels are available to choose from "normalized"
     * @return optLevel +1
     */
    public static int getOptimizationLevels() {
        return (3) + 1;
    }


    /**
     * Instantiates the filter field and populates it with filters the server can filter
     */
    public void createTheFilterDictionary() {
        this.filters = new ArrayList<>();

        Filter f = new Filter();
        f.attribute = "type";
        f.values = new ArrayList<>();
        f.values.add("balloonport");
        f.values.add("heliport");
        f.values.add("small_airport");
        f.values.add("seaplane_base");
        f.values.add("closed");
        f.values.add("medium_airport");
        f.values.add("large_airport");
        this.filters.add(f);

        f = new Filter();
        f.attribute = "continents";
        f.values.add("Africa");
        f.values.add("Antarctica");
        f.values.add("Asia");
        f.values.add("Europe");
        f.values.add("North America");
        f.values.add("Oceania");
        f.values.add("South America");
        this.filters.add(f);
    }

    /**
     * This method does exactly what you think it does.
     * It instantiates the optimizations field and populates it with {(key: value)}
     * I left the actual descriptions in just in case we want to display these eventually.
     */
    public void createTheOptDictionary() {
        this.optimizations = new Dictionary[4];
        this.optimizations = new Dictionary[4];
        Dictionary<String, String> optDict = new Hashtable<>();

        optDict.put("label", "No optimization");
        optDict.put("description", "Longest");
        this.optimizations[0] = optDict;

        optDict = new Hashtable<>();
        optDict.put("label", "Nearest Neighbor");
        optDict.put("description", "Short");
        this.optimizations[1] = optDict;

        optDict = new Hashtable<>();
        optDict.put("label", "2-opt");
        optDict.put("description", "Shorter");
        this.optimizations[2] = optDict;

        optDict = new Hashtable<>();
        optDict.put("label", "3-opt");
        optDict.put("description", "Shortest");
        this.optimizations[3] = optDict;

    }
}
