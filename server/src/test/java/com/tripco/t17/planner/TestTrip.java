package com.tripco.t17.planner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/*
  This class contains tests for the Trip class.
 */
@RunWith(JUnit4.class)
public class TestTrip {
  Trip trip;

  // Setup to be done before every test in TestPlan
  @Before
  public void initialize() {
    trip = new Trip();
  }

  @Test
  public void testTrue() {
    // assertTrue checks if a statement is true
    assertTrue(true == true);
  }

  /**
   * DmsToDegrees Test Block
   */
  @Test
  public void testDmsToDegreesSanity(){
    //Test to make sure 0° = 0°
    //Specific delta as 3rd parameter, as doubles should just be "close enough"
    assertEquals(0.0, trip.DmsToDegrees("0° N"), 0.01);
    assertEquals(0.0, trip.DmsToDegrees("0° S"), 0.01);
    assertEquals(0.0, trip.DmsToDegrees("0° W"), 0.01);
    assertEquals(0.0, trip.DmsToDegrees("0° E"), 0.01);
  }

  @Test
  public void testDmsToDegreesCardinal(){
    //Tests a simple DMS with only degrees and a cardinal direction

    assertEquals(5.0, trip.DmsToDegrees("5° N"), 0.01);
    assertEquals(-5.0, trip.DmsToDegrees("5° S"), 0.01);
    assertEquals(5.0, trip.DmsToDegrees("5° E"), 0.01);
    assertEquals(-5.0, trip.DmsToDegrees("5° W"), 0.01);

  }

  @Test
  public void testDmsToDegreesMinutes1(){
    //Tests a simple DMS with degrees, minutes and a cardinal direction

    assertEquals(5.5, trip.DmsToDegrees("5° 30' N"), 0.01);
    assertEquals(-5.5, trip.DmsToDegrees("5° 30' S"), 0.01);
    assertEquals(5.5, trip.DmsToDegrees("5° 30' E"), 0.01);
    assertEquals(-5.5, trip.DmsToDegrees("5° 30' W"), 0.01);

  }

  @Test
  public void testDmsToDegreesMinutes2(){
    //Tests a simple DMS with degrees, minutes and a cardinal direction
    //
    //    NewZealand.latitude = "41° 19' S";
    //    NewZealand.longitude = "174° 46' E";
    //
    assertEquals(-41.3166, trip.DmsToDegrees("41° 19' S"), 0.01);
    assertEquals(174.7666, trip.DmsToDegrees("174° 46' E"), 0.01);
  }

  @Test
  public void testDmsToDegreesMinutes3(){
    //Tests a simple DMS with degrees, minutes and a cardinal direction
    //
    //    Chile.latitude = "33° 24' S";
    //    Chile.longitude = " 70° 40' W";
    //
    assertEquals(-33.4, trip.DmsToDegrees("33° 24' S"), 0.01);
    assertEquals(-70.6666, trip.DmsToDegrees("70° 40' W"), 0.01);
  }

  @Test
  public void testDmsToDegreesMinutesSeconds0(){
    //Sanity check input
    assertEquals(0.0, trip.DmsToDegrees("0° 0' 0.0\" N"), 0.1);
    assertEquals(0.0, trip.DmsToDegrees("0° 0' 0.0\" S"), 0.1);
    assertEquals(0.0, trip.DmsToDegrees("0° 0' 0.0\" E"), 0.1);
    assertEquals(0.0, trip.DmsToDegrees("0° 0' 0.0\" W"), 0.1);
  }

  @Test
  public void testDmsToDegreesMinutesSeconds1(){
    //Full correct input test, degrees, minutes, seconds, and cardinal direction

    assertEquals(5.55, trip.DmsToDegrees("5° 30' 180\" N"), 0.01);
    assertEquals(-5.55, trip.DmsToDegrees("5° 30' 180\" S"), 0.01);
    assertEquals(5.55, trip.DmsToDegrees("5° 30' 180\" E"), 0.01);
    assertEquals(-5.55, trip.DmsToDegrees("5° 30' 180\" W"), 0.01);
  }

  @Test
  public void testDmsToDegreesMinutesSeconds2(){
    //Full correct input test, degrees, minutes, seconds, and cardinal direction
    //
    //    Canada.latitude = "49° 14' 46.6512\" N";
    //    Canada.longitude = " 123° 6' 58.4136\" S";
    //

    assertEquals(49.2462, trip.DmsToDegrees("49° 14' 46.6512\" N"), 0.0001);
    assertEquals(-123.1162, trip.DmsToDegrees("123° 6' 58.41368\" S"), 0.0001);
  }

  @Test
  public void testDmsToDegreesMinutesSeconds3(){
    //Full correct input test, degrees, minutes, seconds, and cardinal direction
    //
    //    Japan.latitude = "35° 39' 10.1952\" N";
    //    Japan.longitude = "139° 50' 22.1209\" E";
    //

    assertEquals(35.6528, trip.DmsToDegrees("35° 39' 10.1952\" N"), 0.0001);
    assertEquals(139.8394, trip.DmsToDegrees("139° 50' 22.1209\" E"), 0.0001);
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

    assertEquals(35.6528, trip.DmsToDegrees("    35   °    39   '   10.1952   \"   N     "), 0.01);
    assertEquals(139.8394, trip.DmsToDegrees("       139    °      50     '     22.1209      \"    E     "), 0.01);
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

    assertEquals(35.6528, trip.DmsToDegrees("35°39'10.1952\"N"), 0.01);
    assertEquals(139.8394, trip.DmsToDegrees("139°50'22.1209\"E"), 0.01);
  }

/**
 * Place holder test, we will need to make one once we verify our constraints
 */
 /*
  @Test
  public void testDmsToDegreesInvalidInput1(){
    //
  } */


  /**
   * GCD Test Block
   */
  //This is a sanity test
  @Test
  public void testGcdSanity(){
    Place Canada = new Place();
    Canada.latitude = "0° N";
    Canada.longitude = " 0° W";

    Place NewZealand = new Place();
    NewZealand.latitude = "0° S";
    NewZealand.longitude = "0° E";

    //Miles
    assertEquals(0, trip.GCD(Canada, NewZealand, "miles"));

    //Kilometers
    assertEquals(0, trip.GCD(Canada, NewZealand, "kilometers"));
  }


  //This test is from Sprint 1 demo: Canada to New Zealand
  @Test
  public void testGcdSprint1Demo1(){
    Place Canada = new Place();
    Canada.latitude = "49° 14' 46.6512\" N";
    Canada.longitude = " 123° 6' 58.4136\" W";

    Place NewZealand = new Place();
    NewZealand.latitude = "41° 19' S";
    NewZealand.longitude = "174° 46' E";

    //Miles
    assertEquals(7304, trip.GCD(Canada, NewZealand, "miles"));

    //Kilometers
    assertEquals(11755, trip.GCD(Canada, NewZealand, "kilometers"));
  }


  //This test is from Sprint 1 demo: Chile to Japan
  @Test
  public void testGcdSprint1Demo2(){
    Place Chile = new Place();
    Chile.latitude = "33° 24' S";
    Chile.longitude = " 70° 40' W";

    Place Japan = new Place();
    Japan.latitude = "35° 39' 10.1952\" N";
    Japan.longitude = "139° 50' 22.1209\" E";

    //Miles
    int expectedMiles = 10700;
    int actualMiles = trip.GCD(Chile, Japan, "miles");
    assertEquals(expectedMiles, actualMiles);

    //Kilometers
    int expectedKilometers = 17220;
    int actualKilometers = trip.GCD(Chile, Japan, "kilometers");
    assertEquals(expectedKilometers, actualKilometers);
  }


  /**
   * legDistances Test Block
   */
  @Test
  public void testLegDistances() {
    trip.plan();
    ArrayList<Integer> expectedDistances = new ArrayList<Integer>();
    Collections.addAll(expectedDistances, 12, 23, 34, 45, 65, 19);
    // Call the equals() method of the first object on the second object.
    assertEquals(expectedDistances, trip.distances);
  }

}
