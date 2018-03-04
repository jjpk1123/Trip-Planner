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


  /** nearestNeighbor test block
   */
  @Test
  public void testNearestNeighborSameOrder1() {
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "0° E";

    Place C = new Place();
    C.latitude = "2° S";
    C.longitude = "0° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(B);
    places.add(C);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);

    assertEquals(places, Optimize.nearestNeighbor(trip.places));
  }

  @Test
  public void testNearestNeighborSameOrder2() {
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "0° E";

    Place C = new Place();
    C.latitude = "1° S";
    C.longitude = "0° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(B);
    places.add(C);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);

    assertEquals(places, Optimize.nearestNeighbor(trip.places));
  }

  @Test
  public void testNearestNeighborSameOrder3() {
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "0° E";

    Place C = new Place();
    C.latitude = "1° S";
    C.longitude = "0° E";

    Place D = new Place();
    D.latitude = "1° S";
    D.longitude = "0° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(B);
    places.add(C);
    places.add(D);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);
    trip.places.add(D);

    assertEquals(places, Optimize.nearestNeighbor(trip.places));
  }

  @Test
  public void testNearestNeighborReorder1() {
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "30° S";
    B.longitude = "0° E";

    Place C = new Place();
    C.latitude = "1° S";
    C.longitude = "0° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(C);
    places.add(B);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);

    assertEquals(places, Optimize.nearestNeighbor(trip.places));
  }

  @Test
  public void testNearestNeighborReorder2() {
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "30° S";
    B.longitude = "0° E";

    Place C = new Place();
    C.latitude = "2° S";
    C.longitude = "0° E";

    Place D = new Place();
    D.latitude = "40° S";
    D.longitude = "10° E";

    Place E = new Place();
    E.latitude = "0° S";
    E.longitude = "1° W";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(E);
    places.add(C);
    places.add(B);
    places.add(D);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);
    trip.places.add(D);
    trip.places.add(E);

    assertEquals(places, Optimize.nearestNeighbor(trip.places));
  }

  @Test
  public void testNearestNeighborOnePlace(){

    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    trip.places.add(A);

    assertEquals(places, Optimize.nearestNeighbor(trip.places));
  }

  @Test
  public void testNearestNeighborNoPlaces(){
    ArrayList<Place> places = new ArrayList<>();
    assertEquals(places, Optimize.nearestNeighbor(trip.places));
  }

  @Test
  public void testNearestNeighborRepeatedPlaces1() {
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "0° S";
    B.longitude = "0° E";

    Place C = new Place();
    C.latitude = "2° S";
    C.longitude = "0° E";

    Place D = new Place();
    D.latitude = "40° S";
    D.longitude = "10° E";

    Place E = new Place();
    E.latitude = "40° S";
    E.longitude = "10° E";

    Place F = new Place();
    F.latitude = "41° S";
    F.longitude = "10° E";

    Place G = new Place();
    G.latitude = "41° S";
    G.longitude = "10° E";

    Place H = new Place();
    H.latitude = "50° S";
    H.longitude = "50° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(B);
    places.add(C);
    places.add(D);
    places.add(E);
    places.add(F);
    places.add(G);
    places.add(H);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);
    trip.places.add(D);
    trip.places.add(E);
    trip.places.add(F);
    trip.places.add(G);
    trip.places.add(H);

    assertEquals(places, Optimize.nearestNeighbor(trip.places));
  }

  /** nearestNeighbor helper: findNearest test block
   */
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
  public void testFindNearestFavorFirst1(){
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
































