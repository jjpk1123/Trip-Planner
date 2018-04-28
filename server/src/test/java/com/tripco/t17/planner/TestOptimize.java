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
public class TestOptimize {
  private Trip trip;

  // Setup to be done before every test in TestPlan
  @Before
  public void initialize() {
    trip = new Trip();
    trip.places = new ArrayList<>();
    trip.options = new Option();
    trip.options.distance = "miles";
  }

  @Test
  public void testMakeOptimize() {
    Optimize o = new Optimize();
    //That's it...
  }

  /**
   *  nearestNeighbor test block
   */

  @Test
  public void testNearestNeighborSameOrder1() {
    Place A = new Place();
    A.latitude = "1° S";
    A.longitude = " 1° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "2° E";

    Place C = new Place();
    C.latitude = "1° S";
    C.longitude = "3° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(B);
    places.add(C);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);

    assertEquals(places, Optimize.optimize(trip.places, .25));
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

    assertEquals(places, Optimize.optimize(trip.places, .25));
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

    assertEquals(places, Optimize.optimize(trip.places, .25));
  }

  @Test
  public void testNearestNeighborReorder1() {
    Place A = new Place();
    A.latitude = "1° S";
    A.longitude = " 3° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "2° E";

    Place C = new Place();
    C.latitude = "2° S";
    C.longitude = "1° E";

    Place D = new Place();
    D.latitude = "1° S";
    D.longitude = "0° E";

    Place E = new Place();
    E.latitude = "0° S";
    E.longitude = "1° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(C);
    places.add(B);
    places.add(A);
    places.add(E);
    places.add(D);
    trip.places.add(C);
    trip.places.add(B);
    trip.places.add(A);
    trip.places.add(D);
    trip.places.add(E);

    assertEquals(places, Optimize.optimize(trip.places, .25));
  }

  @Test
  public void testNearestNeighborReorder2(){

    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "0° S";
    B.longitude = "5° E";

    Place C = new Place();
    C.latitude = "5° S";
    C.longitude = "0° E";

    Place D = new Place();
    D.latitude = "10° S";
    D.longitude = "10° E";

    ArrayList<Place> places = new ArrayList<>();
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);
    trip.places.add(D);

    places.add(B);
    places.add(A);
    places.add(C);
    places.add(D);

    assertEquals(places, Optimize.optimize(trip.places, .25));
  }

  @Test
  public void testNearestNeighborOnePlace(){

    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    trip.places.add(A);

    assertEquals(places, Optimize.optimize(trip.places, .25));
  }

  @Test
  public void testNearestNeighborNoPlaces(){
    ArrayList<Place> places = new ArrayList<>();
    assertEquals(places, Optimize.optimize(trip.places, .25));
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

    assertEquals(places, Optimize.optimize(trip.places, .25));
  }

  /**
   * 2-opt test block
   */

  @Test
  public void testTwoOpt(){
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = "2.5° E";

    Place B = new Place();
    B.latitude = "0° S";
    B.longitude = "7.5° E";

    Place C = new Place();
    C.latitude = "2.5° S";
    C.longitude = "10° E";

    Place D = new Place();
    D.latitude = "5° S";
    D.longitude = "7.5° E";

    Place E = new Place();
    E.latitude = "5° S";
    E.longitude = "2.5° E";

    Place F = new Place();
    F.latitude = "2.5° S";
    F.longitude = "0° E";

    ArrayList<Place> places = new ArrayList<>();
    trip.places.add(A);
    trip.places.add(D);
    trip.places.add(C);
    trip.places.add(B);
    trip.places.add(E);
    trip.places.add(F);

    places.add(A);
    places.add(F);
    places.add(E);
    places.add(D);
    places.add(C);
    places.add(B);

    assertEquals(places, Optimize.optimize(trip.places, .5));
  }

  /**
   * 3opt Test Block
   */

  @Test
  public void testThreeOpt(){
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = "2.5° E";

    Place B = new Place();
    B.latitude = "0° S";
    B.longitude = "7.5° E";

    Place C = new Place();
    C.latitude = "2.5° S";
    C.longitude = "10° E";

    Place D = new Place();
    D.latitude = "5° S";
    D.longitude = "7.5° E";

    Place E = new Place();
    E.latitude = "5° S";
    E.longitude = "2.5° E";

    Place F = new Place();
    F.latitude = "2.5° S";
    F.longitude = "0° E";

    ArrayList<Place> places = new ArrayList<>();
    trip.places.add(A);
    trip.places.add(D);
    trip.places.add(C);
    trip.places.add(B);
    trip.places.add(E);
    trip.places.add(F);

    places.add(A);
    places.add(F);
    places.add(E);
    places.add(D);
    places.add(C);
    places.add(B);

    assertEquals(places, Optimize.optimize(trip.places, .75));
  }

  /**
   * changeStart test block
   */

  @Test
  public void testChangeStartNormalCase1(){
    Place A = new Place();
    A.latitude = "1° S";
    A.longitude = " 3° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "2° E";

    Place C = new Place();
    C.latitude = "2° S";
    C.longitude = "1° E";

    Place D = new Place();
    D.latitude = "1° S";
    D.longitude = "0° E";

    Place E = new Place();
    E.latitude = "0° S";
    E.longitude = "1° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(B);
    places.add(C);
    places.add(D);
    places.add(E);
    places.add(A);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);
    trip.places.add(D);
    trip.places.add(E);

    assertEquals(places, Optimize.changeStart(trip.places, 1));

  }

  @Test
  public void testChangeStartNoChange1(){
    Place A = new Place();
    A.latitude = "1° S";
    A.longitude = " 3° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "2° E";

    Place C = new Place();
    C.latitude = "2° S";
    C.longitude = "1° E";

    Place D = new Place();
    D.latitude = "1° S";
    D.longitude = "0° E";

    Place E = new Place();
    E.latitude = "0° S";
    E.longitude = "1° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(B);
    places.add(C);
    places.add(D);
    places.add(E);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);
    trip.places.add(D);
    trip.places.add(E);

    assertEquals(places, Optimize.changeStart(trip.places, 0));

  }

  @Test
  public void testChangeStartEdgeCase1(){
    Place A = new Place();
    A.latitude = "1° S";
    A.longitude = " 3° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "2° E";

    Place C = new Place();
    C.latitude = "2° S";
    C.longitude = "1° E";

    Place D = new Place();
    D.latitude = "1° S";
    D.longitude = "0° E";

    Place E = new Place();
    E.latitude = "0° S";
    E.longitude = "1° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(E);
    places.add(A);
    places.add(B);
    places.add(C);
    places.add(D);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);
    trip.places.add(D);
    trip.places.add(E);

    assertEquals(places, Optimize.changeStart(trip.places, 4));

  }

  @Test
  public void testChangeStartBadCase1(){
    Place A = new Place();
    A.latitude = "1° S";
    A.longitude = " 3° E";

    Place B = new Place();
    B.latitude = "1° S";
    B.longitude = "2° E";

    Place C = new Place();
    C.latitude = "2° S";
    C.longitude = "1° E";

    Place D = new Place();
    D.latitude = "1° S";
    D.longitude = "0° E";

    Place E = new Place();
    E.latitude = "0° S";
    E.longitude = "1° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(B);
    places.add(C);
    places.add(D);
    places.add(E);
    trip.places.add(A);
    trip.places.add(B);
    trip.places.add(C);
    trip.places.add(D);
    trip.places.add(E);

    assertEquals(places, Optimize.changeStart(trip.places, 7));
    assertEquals(places, Optimize.changeStart(trip.places, -1));

  }

}




























