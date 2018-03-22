package com.tripco.t17.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t17.server.HTTP;
import spark.Request;

/**
 * Straight-forward class. Gets called for REST API "/config"
 */
public class Config {
    // The variables in this class should reflect TFFI.
    public String type;
    public int version;
    public int optimization;

    /**
     * Called from REST API. Updates values to current configuration
     */
    public void retrieveValues() {
        this.type = "config";
        this.version = 2;       // This value will change over time
        this.optimization = 1;  // This value will change over time

        //optimization of 0 == {Nothing]
        //optimization of 1 == [Nothing, NN]
        //optimization of 2 == [Nothing, NN, 2-opt]
        //optimization of 3 == [Nothing, NN, 2-opt, 3-opt]
    }

    /**
     * Returns many Optimization levels are available to choose from "normalized"
     * @return optLevel +1
     */
    public static int getOptimizationLevels() {
        return (1) + 1;         // This value will change over time
        // CS students start at 0
        // level+1 "normalizes"
    }
}
