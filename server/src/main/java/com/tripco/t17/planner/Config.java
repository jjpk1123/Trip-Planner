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
    public Dictionary[] filters;
//    public List<Dictionary> filters;
    public String[] maps;
    public int optimization;
    public Dictionary[] optimizations;
    public String[] units;


    //    optimization of 0 == {No opt]
    //    optimization of 1 == [No opt, NN]
    //    optimization of 2 == [No opt, NN, 2-opt]
    //    optimization of 3 == [No opt, NN, 2-opt, 3-opt]


    /**
     * Called from REST API. Updates values of the server's configuration file
     */
    public void retrieveValues() {
        this.type = "config";
        this.version = 3;                               // This value will change over time
        createTheFilterDictionary();
        this.maps = new String[]{"svg"};//{"svg", "kml"};
        this.optimization = 3;                          // This value will change over time
        createTheOptDictionary();
        this.units = new String[]{"kilometers", "miles", "nautical miles", "user defined"};
    }

    /**
     * Returns many Optimization levels are available to choose from "normalized"
     * @return optLevel +1
     */
    public static int getOptimizationLevels() {
        return (2) + 1;                                 // This value will change over time
        // CS students start at 0
        // level+1 "normalizes"
    }


    /**
     * Instantiates the filter field and populates it with filters the server can filter
     */
    public void createTheFilterDictionary() {
        this.filters = new Dictionary[1];//ArrayList<Dictionary>();
        Dictionary<String, Object> filterDict = new Hashtable<>();

//        filterDict.put("attribute", "type");
//        List<String> valuesArr = new ArrayList<String>();
//        valuesArr.add("balloonport");
//        valuesArr.add("heliport");
//        valuesArr.add("airport");
//        filterDict.put("values", valuesArr);

//        this.filters.add(filterDict);
        this.filters[0] = filterDict;
    }

    /**
     * This method does exactly what you think it does.
     * It instantiates the optimizations field and populates it with {(key: value)}
     * I left the actual descriptions in just in case we want to display these eventually.
     */
    public void createTheOptDictionary() {
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
