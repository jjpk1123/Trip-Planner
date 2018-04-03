package com.tripco.t17.planner;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestConfig {

    ConfigHelper ch;
    Config config;

    @Before
    public void initialize() {
        this.ch = new ConfigHelper();
        this.config = new Config();

        this.config.type = "config";
        this.config.version = 3;
        this.config.filters = new Dictionary[1];

        Dictionary filterDict = new Hashtable();
        filterDict.put("attribute", "type");
        String[] valuesArr = {"balloonport", "heliport", "airport"};
        filterDict.put("values", valuesArr);
        this.config.filters[0] = filterDict;

        this.config.maps = new String[]{"svg"};         // {"svg", "kml"}
        this.config.optimization = 1;                   // 2 // 3
        this.config.optimizations = new Dictionary[2];  // 3 // 4

        Dictionary optDict = new Hashtable();
        optDict.put("label", "No optimization");
        optDict.put("description", "Longest");
        this.config.optimizations[0] = optDict;

        optDict = new Hashtable();
        optDict.put("label", "Nearest Neighbor");
        optDict.put("description", "Shortest");         // 2-opt="Shorter" // 3-opt="Short"
        this.config.optimizations[1] = optDict;

//        optDict = new Hashtable();
//        optDict.put("label", "2-opt");
//        optDict.put("description", "Shortest");         // 3-opt = "Shorter"
//        this.config.optimizations[2] = optDict;

//        optDict = new Hashtable();
//        optDict.put("label", "3-opt");
//        optDict.put("description", "Shortest");
//        this.config.optimizations[3] = optDict;
        this.config.units = new String[]{"kilometers", "miles", "nautical miles", "user defined"};
    }

    @Test
    public void testConfigType() {
        assertEquals("config", this.config.type);
    }

    @Test
    public void testConfigVersion() {
        assertEquals(3, this.config.version);
    }

    @Test
    public void testConfigFilters() {
        assertEquals("type", this.config.filters[0].get("attribute"));
        assertEquals(Arrays.toString(new String[]{"balloonport", "heliport", "airport"}),
                Arrays.toString((String[]) this.config.filters[0].get("values")));
    }

    @Test
    public void testConfigMaps() {
        String[] expected = new String[1];              // 2
        expected[0] = "svg";
//        expected[1] = "kml";
        assertEquals(Arrays.toString(expected), Arrays.toString(this.config.maps));
    }

    @Test
    public void testConfigOptimization() {
        assertEquals(1, this.config.optimization);
    }

    @Test
    public void testConfigOptimizations() {
        assertEquals("No optimization", this.config.optimizations[0].get("label"));
        assertEquals("Longest", this.config.optimizations[0].get("description"));

        assertEquals("Nearest Neighbor", this.config.optimizations[1].get("label"));
        assertEquals("Shortest", this.config.optimizations[1].get("description"));

//        assertEquals("2-opt", this.config.optimizations[2].get("label"));
//        assertEquals("2-opt=Shortest ***||*** 3-opt=Shorter", this.config.optimizations[2].get("description"));
//
//        assertEquals("3-opt", this.config.optimizations[3].get("label"));
//        assertEquals("Shortest", this.config.optimizations[3].get("description"));

    }

    @Test
    public void testConfigUnits() {
        String[] expected = new String[4];
        expected[0] = "kilometers";
        expected[1] = "miles";
        expected[2] = "nautical miles";
        expected[3] = "user defined";

        assertEquals(Arrays.toString(expected), Arrays.toString(this.config.units));
    }

    /**
     * Tests the standalone method that return the "normalized" optimization levels
     */
    @Test
    public void testConfigGetOptimizationLevels() {
        assertEquals(2, Config.getOptimizationLevels());
    }

    @Test
    public void testGetConfig() {
        Gson gson = new Gson();
        String expected = gson.toJson(this.config);

        assertEquals(expected, ch.getConfig());
    }
}
