package com.tripco.t17.planner;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDistance {
    Trip trip;

    // Setup to be done before every test in TestPlan
    @Before
    public void initialize() {
        trip = new Trip();
        trip.places = new ArrayList<>();
        trip.options = new Option();
        trip.options.distance = "miles";
    }

    @Test
    public void testInitDistance() {
        Distance d = new Distance();
        //That's it...
    }

    /**
     * DmsToDegrees Test Block
     */
    @Test
    public void testDmsToDegreesSanity(){
        //Test to make sure 0° = 0°
        //Specific delta as 3rd parameter, as doubles should just be "close enough"
        assertEquals("testDmsToDegreesSanity: 0° N", 0.0, Distance.dmsToDegrees("0° N"), 0.01);
        assertEquals("testDmsToDegreesSanity: 0° S", 0.0, Distance.dmsToDegrees("0° S"), 0.01);
        assertEquals("testDmsToDegreesSanity: 0° W", 0.0, Distance.dmsToDegrees("0° W"), 0.01);
        assertEquals("testDmsToDegreesSanity: 0° E", 0.0, Distance.dmsToDegrees("0° E"), 0.01);
    }

    @Test
    public void testDmsToDegreesNoWork(){
        assertEquals(0.0, Distance.dmsToDegrees("0"), 0.01);
        assertEquals(0.0, Distance.dmsToDegrees("0"), 0.01);
        assertEquals(0.0, Distance.dmsToDegrees("0"), 0.01);
        assertEquals(0.0, Distance.dmsToDegrees("0"), 0.01);
    }

    @Test
    public void testDmsToDegreesCardinal(){
        //Tests a simple DMS with only degrees and a cardinal direction

        assertEquals(5.0, Distance.dmsToDegrees("5° N"), 0.01);
        assertEquals(-5.0, Distance.dmsToDegrees("5° S"), 0.01);
        assertEquals(5.0, Distance.dmsToDegrees("5° E"), 0.01);
        assertEquals(-5.0, Distance.dmsToDegrees("5° W"), 0.01);

    }

    @Test
    public void testDmsToDegreesMinutes1(){
        //Tests a simple DMS with degrees, minutes and a cardinal direction

        assertEquals(5.5, Distance.dmsToDegrees("5° 30' N"), 0.01);
        assertEquals(-5.5, Distance.dmsToDegrees("5° 30' S"), 0.01);
        assertEquals(5.5, Distance.dmsToDegrees("5° 30' E"), 0.01);
        assertEquals(-5.5, Distance.dmsToDegrees("5° 30' W"), 0.01);

    }

    @Test
    public void testDmsToDegreesMinutes2(){
        //Tests a simple DMS with degrees, minutes and a cardinal direction
        //
        //    NewZealand.latitude = "41° 19' S";
        //    NewZealand.longitude = "174° 46' E";
        //
        assertEquals(-41.3166, Distance.dmsToDegrees("41° 19' S"), 0.01);
        assertEquals(174.7666, Distance.dmsToDegrees("174° 46' E"), 0.01);
    }

    @Test
    public void testDmsToDegreesMinutes3(){
        //Tests a simple DMS with degrees, minutes and a cardinal direction
        //
        //    Chile.latitude = "33° 24' S";
        //    Chile.longitude = " 70° 40' W";
        //
        assertEquals(-33.4, Distance.dmsToDegrees("33° 24' S"), 0.01);
        assertEquals(-70.6666, Distance.dmsToDegrees("70° 40' W"), 0.01);
    }

    @Test
    public void testDmsToDegreesMinutesSeconds0(){
        //Sanity check input
        assertEquals(0.0, Distance.dmsToDegrees("0° 0' 0.0\" N"), 0.1);
        assertEquals(0.0, Distance.dmsToDegrees("0° 0' 0.0\" S"), 0.1);
        assertEquals(0.0, Distance.dmsToDegrees("0° 0' 0.0\" E"), 0.1);
        assertEquals(0.0, Distance.dmsToDegrees("0° 0' 0.0\" W"), 0.1);
    }

    @Test
    public void testDmsToDegreesMinutesSeconds1(){
        //Full correct input test, degrees, minutes, seconds, and cardinal direction

        assertEquals(5.55, Distance.dmsToDegrees("5° 30' 180\" N"), 0.01);
        assertEquals(-5.55, Distance.dmsToDegrees("5° 30' 180\" S"), 0.01);
        assertEquals(5.55, Distance.dmsToDegrees("5° 30' 180\" E"), 0.01);
        assertEquals(-5.55, Distance.dmsToDegrees("5° 30' 180\" W"), 0.01);
    }

    @Test
    public void testDmsToDegreesMinutesSeconds2(){
        //Full correct input test, degrees, minutes, seconds, and cardinal direction
        //
        //    Canada.latitude = "49° 14' 46.6512\" N";
        //    Canada.longitude = " 123° 6' 58.4136\" S";
        //

        assertEquals(49.2462, Distance.dmsToDegrees("49° 14' 46.6512\" N"), 0.0001);
        assertEquals(-123.1162, Distance.dmsToDegrees("123° 6' 58.41368\" S"), 0.0001);
    }

    @Test
    public void testDmsToDegreesMinutesSeconds3(){
        //Full correct input test, degrees, minutes, seconds, and cardinal direction
        //
        //    Japan.latitude = "35° 39' 10.1952\" N";
        //    Japan.longitude = "139° 50' 22.1209\" E";
        //

        assertEquals(35.6528, Distance.dmsToDegrees("35° 39' 10.1952\" N"), 0.0001);
        assertEquals(139.8394, Distance.dmsToDegrees("139° 50' 22.1209\" E"), 0.0001);
    }

    @Test
    public void testDmsToDegreesMinutesSeconds4(){
        //Full correct input test, degrees, minutes, seconds, and cardinal direction
        //IDENTICAL to test 3...
        //HOWEVER, This one contains weird spaces!!!!
        //
        //    Japan.latitude = "35° 39' 10.1952\" N";
        //    Japan.longitude = "139° 50' 22.1209\" E";
        //

        assertEquals(35.6528, Distance.dmsToDegrees("    35   °    39   '   10.1952   \"   N     "), 0.01);
        assertEquals(139.8394, Distance.dmsToDegrees("       139    °      50     '     22.1209      \"    E     "), 0.01);
    }

    @Test
    public void testDmsToDegreesMinutesSeconds5(){
        //Full correct input test, degrees, minutes, seconds, and cardinal direction
        //IDENTICAL to test 3...
        //HOWEVER, This one contains NO spaces!!!!
        //
        //    Japan.latitude = "35° 39' 10.1952\" N";
        //    Japan.longitude = "139° 50' 22.1209\" E";
        //

        assertEquals(35.6528, Distance.dmsToDegrees("35°39'10.1952\"N"), 0.01);
        assertEquals(139.8394, Distance.dmsToDegrees("139°50'22.1209\"E"), 0.01);
    }


    /**
     * GCD Test Block
     */
    //This is a sanity test
    @Test
    public void testGcdSanity(){
        Place Canada = new Place();
        Canada.latitude  = "0° N";
        Canada.longitude = "0° W";

        Place NewZealand = new Place();
        NewZealand.latitude  = "0° S";
        NewZealand.longitude = "0° E";

        //Miles
        assertEquals(0, Distance.gcd(Canada, NewZealand, "miles", ""));

        //Kilometers
        assertEquals(0, Distance.gcd(Canada, NewZealand, "kilometers", ""));
    }


    //This test is from Sprint 1 demo: Canada to New Zealand
    @Test
    public void testGcdSprint1Demo1(){
        Place Canada = new Place();
        Canada.latitude  = "49°  14' 46.6512\" N";
        Canada.longitude = "123°  6' 58.4136\" W";

        Place NewZealand = new Place();
        NewZealand.latitude  = "41°  19' S";
        NewZealand.longitude = "174° 46' E";

        //Miles
        assertEquals(7304, Distance.gcd(Canada, NewZealand, "miles", ""));

        //Kilometers
        assertEquals(11755, Distance.gcd(Canada, NewZealand, "kilometers", ""));
    }


    //This test is from Sprint 1 demo: Chile to Japan
    @Test
    public void testGcdSprint1Demo2(){
        Place Chile = new Place();
        Chile.latitude  = "33° 24' S";
        Chile.longitude = "70° 40' W";

        Place Japan = new Place();
        Japan.latitude  = " 35° 39' 10.1952\" N";
        Japan.longitude = "139° 50' 22.1209\" E";

        //Miles
        int expectedMiles = 10700;
        int actualMiles = Distance.gcd(Chile, Japan, "miles", "");
        assertEquals(expectedMiles, actualMiles);

        //Kilometers
        int expectedKilometers = 17220;
        int actualKilometers = Distance.gcd(Chile, Japan, "kilometers", "");
        assertEquals(expectedKilometers, actualKilometers);
    }

    @Test
    public void testUserDefinedDistance_Feet() {
        Place Budapest = new Place();
        Budapest.latitude  = "47° 29' 52\" N";
        Budapest.longitude = "19°  2' 23\" E";

        Place Istanbul = new Place();
        Istanbul.latitude  = "41°  0' 54\" N";
        Istanbul.longitude = "28° 58' 46\" E";

        int expectedFeet = 3511411;
        int actualFeet = Distance.gcd(Budapest, Istanbul, "user defined", "20925721.7847");
        assertEquals(expectedFeet, actualFeet);
    }

    @Test
    public void testUserDefinedDistance_Inches() {
        Place Budapest = new Place();
        Budapest.latitude  = "47° 29' 52\" N";
        Budapest.longitude = "19°  2' 23\" E";

        Place Istanbul = new Place();
        Istanbul.latitude  = "41°  0' 54\" N";
        Istanbul.longitude = "28° 58' 46\" E";

        int expectedInches = 42136929;
        int actualInches = Distance.gcd(Budapest, Istanbul, "user defined", "251108661.4219");
        assertEquals(expectedInches, actualInches);
    }

    @Test
    public void testInvalidPlace() {
        Place Incorrect = new Place();
        Incorrect.latitude  = "Bad Source";
        Incorrect.longitude = "0° 0' 0\" E";

        Place Correct = new Place();
        Correct.latitude  = "2° 3' 4\" N";
        Correct.longitude = "2° 3' 4\" E";

        Place Compare = new Place();
        Compare.latitude  = "10° 3' 4\" N";
        Compare.longitude = "10° 3' 4\" N";

        int negOne = Distance.gcd(Incorrect, Correct, "miles", "");
        assertEquals(-1, negOne);

        Incorrect.latitude = "Bad Dest";
        int negTwo = Distance.gcd(Correct, Incorrect, "miles", "");
        assertEquals(-2, negTwo);

        int expectedNM = 677;
        int actualNM = Distance.gcd(Correct, Compare, "nautical miles", "");
        assertEquals(expectedNM, actualNM);

        actualNM = Distance.gcd(Correct, Compare, "nautical miles", "");

        assertEquals(expectedNM, actualNM);
    }


    @Test
    public void testLegDistances() {
        Place Telluride = new Place();
        Telluride.latitude    = "37°  56' 11 N";
        Telluride.longitude   = "108° 50' 46 W";

        Place Monarch = new Place();
        Monarch.latitude      = "38 ° 30' 48 N";
        Monarch.longitude     = "105° 19' 57 W";

        Place Silverton = new Place();
        Silverton.latitude    = "39°  53' 1  N";
        Silverton.longitude   = "107° 40' 2  W";

        Place Purgatory = new Place();
        Purgatory.latitude    = "37°  37' 49 N";
        Purgatory.longitude   = "107° 48' 52 W";

        trip.places.add(Telluride);
        trip.places.add(Monarch);
        trip.places.add(Silverton);
        trip.places.add(Purgatory);

        trip.options.optimization= "0.5";
        trip.plan();

        ArrayList<Integer> expectedDistances = new ArrayList<>();
        Collections.addAll(expectedDistances, 195, 158, 157, 61);
        ArrayList<Integer> actualDistances = Distance.legDistances(trip.places, trip.options.distance, "");
        assertEquals(expectedDistances, actualDistances);
    }

}
