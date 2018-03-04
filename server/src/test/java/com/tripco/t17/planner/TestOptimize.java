package com.tripco.t17.planner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
  This class contains tests for the Trip class.
 */
@RunWith(JUnit4.class)
public class TestOptimize {
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
  public void testTrue() {
    // assertTrue checks if a statement is true
    assertTrue(true == true);
  }

 /* @Test
  public void testNearestNeighborSanity1() {
    Place Montreal = new Place();
    Place NewZealand = new Place();
    Place Tokyo = new Place();
    trip.places.add(Montreal);
    trip.places.add(NewZealand);
    trip.places.add(Tokyo);

    ArrayList<Place> actual = Optimize.nearestNeighbor(trip.places);

    assertEquals(trip.places, actual);
  }*/

  @Test
  public void testFindNearest1(){
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "0° E";

    Place C = new Place();
    C.latitude = "7° S";
    C.longitude = "0° E";

    ArrayList<Place> unvisited = new ArrayList<>();
    Place start = A;
    unvisited.add(B);
    unvisited.add(C);

    assertEquals(0, Optimize.findNearest(start, unvisited));
  }

  @Test
  public void testFindNearest2(){
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "12° S";
    B.longitude = "0° E";

    Place C = new Place();
    C.latitude = "1° S";
    C.longitude = "0° E";

    ArrayList<Place> unvisited = new ArrayList<>();
    Place start = A;
    unvisited.add(B);
    unvisited.add(C);

    assertEquals(1, Optimize.findNearest(start, unvisited));
  }

  @Test
  public void testFindNearestFavorFirst(){
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "0° E";

    Place C = new Place();
    C.latitude = "1° S";
    C.longitude = "0° E";

    ArrayList<Place> unvisited = new ArrayList<>();
    Place start = A;
    unvisited.add(B);
    unvisited.add(C);

    assertEquals(0, Optimize.findNearest(start, unvisited));
  }
}
































