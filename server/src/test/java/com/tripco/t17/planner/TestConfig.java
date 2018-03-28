package com.tripco.t17.planner;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestConfig {

    ConfigHelper ch;

    @Before
    public void initialize() {
        ch = new ConfigHelper();
    }

    @Test
    public void testInit() {
        assertTrue(true == true);
    }

    @Test
    public void testGetConfig() {
        Config expectedConfig = new Config();
        expectedConfig.type = "config";
        expectedConfig.version = 2;
        expectedConfig.optimization = 1;
        Gson gson = new Gson();
        String expected = gson.toJson(expectedConfig);

        assertEquals(expected, ch.getConfig());
    }

    @Test
    public void testConfigGetOptimizationLevels() {
        assertEquals(2, Config.getOptimizationLevels());
    }
}
