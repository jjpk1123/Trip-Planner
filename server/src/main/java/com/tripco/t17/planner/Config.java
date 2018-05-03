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
    public Filter[] filters;
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
        this.filters = new Filter[1];

        Filter f1 = new Filter();
        f1.attribute = "type";
        f1.values = new String[7];
        f1.values[0] = "balloonport";
        f1.values[1] = "heliport";
        f1.values[2] = "small_airport";
        f1.values[3] = "seaplane_base";
        f1.values[4] = "closed";
        f1.values[5] = "medium_airport";
        f1.values[6] = "large_airport";
        this.filters[0] = f1;

//        Filter f2 = new Filter();
//        f2.attribute = "continents";
//        f2.values = new ArrayList<>();
//        f2.values.add("Africa");
//        f2.values.add("Antarctica");
//        f2.values.add("Asia");
//        f2.values.add("Europe");
//        f2.values.add("North America");
//        f2.values.add("Oceania");
//        f2.values.add("South America");
//        this.filters.add(f2);
    }

    /**
     * This method does exactly what you think it does.
     * It instantiates the optimizations field and populates it with {(key: value)}
     * I left the actual descriptions in just in case we want to display these eventually.
     */
    public void createTheOptDictionary() {
        this.optimizations = new Dictionary[4];
        Dictionary<String, String> optDict;

        optDict = new Hashtable<>();
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
