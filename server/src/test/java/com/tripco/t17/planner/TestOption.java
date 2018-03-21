package com.tripco.t17.planner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
  This class contains tests for the Trip class.
 */
@RunWith(JUnit4.class)
public class TestOption {
    Trip trip;

    // Setup to be done before every test in TestPlan
    @Before
    public void initialize() {
        trip = new Trip();
        trip.options = new Option();
    }

    @Test
    public void testNone() {
        trip.options.optimization = "none";
        assertFalse("testNone: none", Option.optimizeCheck(trip.options.optimization));
    }

    @Test
    public void testZero() {
        trip.options.optimization = "0";
        assertFalse("testZero: 0", Option.optimizeCheck(trip.options.optimization));
    }

    @Test
    public void testZeroPointZero() {
        trip.options.optimization = "0.0";
        assertFalse("testZeroPointZero: 0.0", Option.optimizeCheck(trip.options.optimization));
    }

    @Test
    public void testOne() {
        trip.options.optimization = "1";
        assertTrue("testOne: 1", Option.optimizeCheck(trip.options.optimization));
    }

    @Test
    public void testOnePointZero() {
        trip.options.optimization = "1.0";
        assertTrue("testOnePointZero: 1.0", Option.optimizeCheck(trip.options.optimization));
    }
}
