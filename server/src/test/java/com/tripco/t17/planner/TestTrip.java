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

  // Setup to be done before every test in TestTrip
  @Before
  public void initialize() {
    trip = new Trip();
    trip.places = new ArrayList<>();
    trip.options = new Option();
    trip.options.distance = "miles";
    trip.options.optimization = "0";
  }

  @Test
  public void testTrue() {
    // assertTrue checks if a statement is true
    assertTrue(true == true);
  }

  /**
   * legDistances Test Block
   */

  @Test
  public void testLegDistancesMilesNoPlaces()   {
    trip.plan();
    ArrayList<Integer> expectedDistances = new ArrayList<>();
    assertEquals(expectedDistances, trip.distances);
  }

  @Test
  public void testLegDistancesMilesOnePlace1() {
    //One place once
    Place A = new Place();
    A.id = "start";
    A.name = "Start";
    A.latitude = "35° N";
    A.longitude = "35° E";

    trip.places.add(A);
    trip.plan();
    ArrayList<Integer> expectedDistances = new ArrayList<>();
    Collections.addAll(expectedDistances, 0);
    assertEquals(expectedDistances, trip.distances);
  }


  @Test
  public void testLegDistancesMilesOnePlace2() {
    //One place twice
    Place A = new Place();
    A.id = "start";
    A.name = "Start";
    A.latitude = "35° N";
    A.longitude = "35° E";

    trip.places.add(A);
    trip.places.add(A);
    trip.plan();
    ArrayList<Integer> expectedDistances = new ArrayList<>();
    Collections.addAll(expectedDistances, 0, 0);
    assertEquals(expectedDistances, trip.distances);
  }

  @Test
  public void testLegDistancesMilesOnePlace3() {
    //One place twice
    Place A = new Place();
    A.id = "start";
    A.name = "Start";
    A.latitude = "35° N";
    A.longitude = "35° E";

    trip.places.add(A);
    trip.places.add(A);
    trip.places.add(A);
    trip.plan();
    ArrayList<Integer> expectedDistances = new ArrayList<>();
    Collections.addAll(expectedDistances, 0, 0, 0);
    assertEquals(expectedDistances, trip.distances);
  }

  @Test
  public void testLegDistancesMilesTwoPlaces() {
    //Two places
    Place Chile = new Place();
    Chile.latitude = "33° 24' S";
    Chile.longitude = " 70° 40' W";

    Place Japan = new Place();
    Japan.latitude = "35° 39' 10.1952\" N";
    Japan.longitude = "139° 50' 22.1209\" E";

    trip.places.add(Japan);
    trip.places.add(Chile);

    //Miles
    ArrayList<Integer> expectedDistances = new ArrayList<>();
    Collections.addAll(expectedDistances, 10700, 10700);
    trip.plan();
    assertEquals(expectedDistances, trip.distances);

    //Kilometers
    trip.options.distance = "kilometers";
    expectedDistances = new ArrayList<>();
    Collections.addAll(expectedDistances, 17220, 17220);
    trip.plan();
    assertEquals(expectedDistances, trip.distances);
  }


  @Test
  public void testLegDistancesMilesThreePlaces() {
    //Two places
    Place Denver = new Place();
    Denver.latitude = "39.739236";
    Denver.longitude = "-104.990251";

    Place Boulder = new Place();
    Boulder.latitude = "40.014986";
    Boulder.longitude = "-105.270546";

    Place FoCo = new Place();
    FoCo.latitude = "40.585260";
    FoCo.longitude = "-105.084423";

    trip.places.add(Denver);
    trip.places.add(Boulder);
    trip.places.add(FoCo);

    //Miles
    ArrayList<Integer> expectedDistances = new ArrayList<>();
    Collections.addAll(expectedDistances, 24, 41, 59);
    trip.plan();
    assertEquals(expectedDistances, trip.distances);

    //Kilometers
    trip.options.distance = "kilometers";
    expectedDistances = new ArrayList<>();
    Collections.addAll(expectedDistances, 39, 65, 94);
    trip.plan();
    assertEquals(expectedDistances, trip.distances);
  }


}
