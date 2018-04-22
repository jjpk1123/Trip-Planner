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

  /** nearestNeighbor test block
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

    assertEquals(places, Optimize.optimize(trip.places, .5));
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

    assertEquals(places, Optimize.optimize(trip.places, .5));
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

    assertEquals(places, Optimize.optimize(trip.places, .5));
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

    assertEquals(places, Optimize.optimize(trip.places, .5));
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

    assertEquals(places, Optimize.optimize(trip.places, .5));
  }

  @Test
  public void testNearestNeighborOnePlace(){

    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    trip.places.add(A);

    assertEquals(places, Optimize.optimize(trip.places, .5));
  }

  @Test
  public void testNearestNeighborNoPlaces(){
    ArrayList<Place> places = new ArrayList<>();
    assertEquals(places, Optimize.optimize(trip.places, .5));
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

    assertEquals(places, Optimize.optimize(trip.places, .5));
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

  /**
   * buildPlacesArray test block.
   */

  @Test
  public void testBuildPlacesArrayNormalUse(){
    int [] hello = Optimize.buildPlacesArray(5);
    for (int i = 0 ; i < hello.length ; i++){
      assertEquals(i, hello[i]);

    }
  }

  /**
   * buildDistanceTable test block.
   */

  @Test
  public void testBuildDistanceTableDiagonal(){
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
    places.add(A);
    places.add(B);
    places.add(C);
    places.add(D);

    int [][] actual = Optimize.buildDistanceTable(places);
    for (int i = 0 ; i < places.size() ; i++){
      assertEquals(0, actual[i][i]);
    }
  }

  @Test
  public void testBuildDistanceTableOnePlace(){
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);

    int [][] actual = Optimize.buildDistanceTable(places);
    assertEquals(0, actual[0][0]);
  }

  @Test
  public void testBuildDistanceTableSymmetry(){
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
    places.add(A);
    places.add(B);
    places.add(C);
    places.add(D);

    int [][] actual = Optimize.buildDistanceTable(places);
    for (int i = 0 ; i < places.size() ; i++){
      for (int j = 0 ; j < places.size() ; j++) {
        assertEquals(actual[i][j], actual[j][i]);
      }
    }
  }

  @Test
  public void testBuildDistanceTableAccuracy(){
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "0° S";
    B.longitude = "5° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(B);

    int [][] actual = Optimize.buildDistanceTable(places);
    assertEquals(Distance.gcd(A, B, "miles", ""), actual[0][1]);
    assertEquals(Distance.gcd(A, B, "miles", ""), actual[1][0]);
    assertEquals(Distance.gcd(B, A, "miles", ""), actual[0][1]);
    assertEquals(Distance.gcd(B, A, "miles", ""), actual[1][0]);
  }

  @Test
  public void testBuildDistanceTableAllValues(){
    Place A = new Place();
    A.latitude = "0° S";
    A.longitude = " 0° E";

    Place B = new Place();
    B.latitude = "0° S";
    B.longitude = "5° E";

    Place C = new Place();
    C.latitude = "5° S";
    C.longitude = "0° E";

    ArrayList<Place> places = new ArrayList<>();
    places.add(A);
    places.add(B);
    places.add(C);

    int actual [][] = Optimize.buildDistanceTable(places);
    assertEquals(Distance.gcd(A, B, "miles", ""), actual[0][1]);
    assertEquals(Distance.gcd(B, A, "miles", ""), actual[1][0]);
    assertEquals(Distance.gcd(A, B, "miles", ""), actual[0][1]);
    assertEquals(Distance.gcd(B, A, "miles", ""), actual[1][0]);
    assertEquals(Distance.gcd(A, C, "miles", ""), actual[0][2]);
    assertEquals(Distance.gcd(B, C, "miles", ""), actual[1][2]);
  }

  /**
   * Swap test "block".
   */
  @Test
  public void testSwapSimple(){
    int [] myArray = new int [2];
    myArray[0] = 3;
    myArray[1] = 5;
    Optimize.swap(myArray, 0, 1);

    assertEquals(3, myArray[1]);
    assertEquals(5, myArray[0]);

    Optimize.swap(myArray, 1, 0);
    assertEquals(5, myArray[1]);
    assertEquals(3, myArray[0]);
  }

  /**
   * NearestNeighborHelper test block.
   */

  @Test
  public void testNearestNeighborSimple1(){
    int start = 0;

    // [0, 1]
    int [] placesArray = Optimize.buildPlacesArray(2);

    /* [0, 1]
       [1, 0] */
    int [][] distanceTable = new int [2][2];

    //Diagonal
    distanceTable[0][0] = 0;
    distanceTable[1][1] = 0;

    //Row 1
    distanceTable[0][1] = 1;
    distanceTable[1][0] = 1;

    assertEquals(2, Optimize.nearestNeighbor(start, placesArray, distanceTable));
  }

  @Test
  public void testNearestNeighborSimple2(){
    int start = 0;

    // [0, 1, 2, 3]
    int [] placesArray = Optimize.buildPlacesArray(3);

    /* [0, 3, 4]
       [3, 0, 5]
       [4, 5, 0] */
    int [][] distanceTable = new int [3][3];

    //Diagonal
    distanceTable[0][0] = 0;
    distanceTable[1][1] = 0;
    distanceTable[2][2] = 0;

    //Row 1
    distanceTable[0][1] = 3;
    distanceTable[1][0] = 3;
    distanceTable[0][2] = 4;
    distanceTable[2][0] = 4;

    //Row 2
    distanceTable[1][2] = 5;
    distanceTable[2][1] = 5;

    assertEquals(12, Optimize.nearestNeighbor(start, placesArray, distanceTable));
  }

  @Test
  public void testNearestNeighborProblem1(){
    int start = 0;

    // [0, 1, 2, 3]
    int [] placesArray = Optimize.buildPlacesArray(4);

    /* [0, 3, 2, 1]
       [3, 0, 1, 5]
       [2, 1, 0, 4]
       [1, 5, 4, 0] */
    int [][] distanceTable = new int [4][4];

    //Diagonal
    distanceTable[0][0] = 0;
    distanceTable[1][1] = 0;
    distanceTable[2][2] = 0;
    distanceTable[3][3] = 0;

    //Row 1
    distanceTable[0][1] = 3;
    distanceTable[1][0] = 3;
    distanceTable[0][2] = 2;
    distanceTable[2][0] = 2;
    distanceTable[0][3] = 1;
    distanceTable[3][0] = 1;

    //Row 2
    distanceTable[1][2] = 1;
    distanceTable[2][1] = 1;
    distanceTable[1][3] = 5;
    distanceTable[3][1] = 5;

    //Row 3
    distanceTable[2][3] = 4;
    distanceTable[3][2] = 4;

    //Should grab 1, 4, 1, 3 = 9
    assertEquals(9, Optimize.nearestNeighbor(start, placesArray, distanceTable));
  }

  @Test
  public void testNearestNeighborProblem2(){
    int start = 0;

    // [0, 1, 2, 3, 4]
    int [] placesArray = Optimize.buildPlacesArray(5);

    /* [0, 6, 5, 1, 6]
       [6, 0, 4, 6, 3]
       [5, 4, 0, 6, 6]
       [1, 6, 6, 0, 2]
       [6, 3, 6, 2, 0]
     */
    int [][] distanceTable = new int [5][5];

    //Diagonal
    distanceTable[0][0] = 0;
    distanceTable[1][1] = 0;
    distanceTable[2][2] = 0;
    distanceTable[3][3] = 0;
    distanceTable[4][4] = 0;

    //Row 1
    distanceTable[0][1] = 6;
    distanceTable[1][0] = 6;
    distanceTable[0][2] = 5;
    distanceTable[2][0] = 5;
    distanceTable[0][3] = 1;
    distanceTable[3][0] = 1;
    distanceTable[0][4] = 6;
    distanceTable[4][0] = 6;

    //Row 2
    distanceTable[1][2] = 4;
    distanceTable[2][1] = 4;
    distanceTable[1][3] = 6;
    distanceTable[3][1] = 6;
    distanceTable[1][4] = 3;
    distanceTable[4][1] = 3;

    //Row 3
    distanceTable[2][3] = 6;
    distanceTable[3][2] = 6;
    distanceTable[2][4] = 6;
    distanceTable[4][2] = 6;

    //Row 4
    distanceTable[3][4] = 2;
    distanceTable[4][3] = 2;

    //Should grab 1, 4, 1, 3 = 9
    assertEquals(15, Optimize.nearestNeighbor(start, placesArray, distanceTable));
  }

/**
 * indexOf test block
 */

  @Test
  public void testIndexOf(){
    int[] array = {0, 1, 2, 3, 4};

    int actualReturn = Optimize.indexOf(array, -1);
    assertEquals(-1, actualReturn);

    actualReturn = Optimize.indexOf(array, 4);
    assertEquals(4, actualReturn);
  }

/**
 * startingTripDistance test block
 */

  @Test
  public void testStartingTripDistance(){

  }

/**
 * 2-opt test block
 */

  @Test
  public void testTwoOpt(){
    // [0, 1, 2, 3, 4]
    int [] placesArray = Optimize.buildPlacesArray(5);

  /* [0, 6, 5, 1, 6]
     [6, 0, 4, 6, 3]
     [5, 4, 0, 6, 6]
     [1, 6, 6, 0, 2]
     [6, 3, 6, 2, 0]
   */
    int [][] distanceTable = new int [5][5];

    //Diagonal
    distanceTable[0][0] = 0;
    distanceTable[1][1] = 0;
    distanceTable[2][2] = 0;
    distanceTable[3][3] = 0;
    distanceTable[4][4] = 0;

    //Row 1
    distanceTable[0][1] = 6;
    distanceTable[1][0] = 6;
    distanceTable[0][2] = 5;
    distanceTable[2][0] = 5;
    distanceTable[0][3] = 1;
    distanceTable[3][0] = 1;
    distanceTable[0][4] = 6;
    distanceTable[4][0] = 6;

    //Row 2
    distanceTable[1][2] = 4;
    distanceTable[2][1] = 4;
    distanceTable[1][3] = 6;
    distanceTable[3][1] = 6;
    distanceTable[1][4] = 3;
    distanceTable[4][1] = 3;

    //Row 3
    distanceTable[2][3] = 6;
    distanceTable[3][2] = 6;
    distanceTable[2][4] = 6;
    distanceTable[4][2] = 6;

    //Row 4
    distanceTable[3][4] = 2;
    distanceTable[4][3] = 2;

    //Should grab 1, 4, 1, 3 = 9
    assertEquals(23, Optimize.twoOpt(placesArray, distanceTable));
  }

  @Test
  public void testTwoOptReverseSimple(){
      int [] test = new int [5];
      test[0] = 3;
      test[1] = 5;
      test[2] = 7;
      test[3] = 9;
      test[4] = 11;
      Optimize.reversePlaces(test, 1, 2);
      assertEquals(7, test[1]);
      assertEquals(5, test[2]);
      Optimize.reversePlaces(test, 3, 4);
      assertEquals(11, test[3]);
      assertEquals(9, test[4]);

  }

  @Test
  public void testDis(){
      int [] placesArray = new int [4];
      placesArray[0] = 0;
      placesArray[1] = 1;
      placesArray[2] = 2;
      placesArray[3] = 3;
      int[][] distanceTable = new int[4][4];

      distanceTable[0][0] = 0;
      distanceTable[1][1] = 0;
      distanceTable[2][2] = 0;
      distanceTable[3][3] = 0;

      //Row 1
      distanceTable[0][1] = 3;
      distanceTable[1][0] = 3;
      distanceTable[0][2] = 2;
      distanceTable[2][0] = 2;
      distanceTable[0][3] = 1;
      distanceTable[3][0] = 1;

      //Row 2
      distanceTable[1][2] = 1;
      distanceTable[2][1] = 1;
      distanceTable[1][3] = 5;
      distanceTable[3][1] = 5;

      //Row 3
      distanceTable[2][3] = 4;
      distanceTable[3][2] = 4;


      int result = Optimize.dis(placesArray, distanceTable, 0, 1);
      assertEquals(3, result);
      result = Optimize.dis(placesArray, distanceTable, 2, 3);
      assertEquals(4, result);
      result = Optimize.dis(placesArray, distanceTable, 0, 2);
      assertEquals(2, result);
      result = Optimize.dis(placesArray, distanceTable, 1, 3);
      assertEquals(5, result);

      //delta should be -3 -4 +2 + 5 = 0 AKA no improvement
      int delta = -Optimize.dis(placesArray, distanceTable, 0, 1)-Optimize.dis(placesArray, distanceTable,2,3)
              +Optimize.dis(placesArray,distanceTable,0,2)+Optimize.dis(placesArray,distanceTable,1,3);

      assertEquals(0, delta);

  }

/**
 * 3opt Test Block
 */

  @Test
  public void testThreeOpt(){
    int [][] distanceTable = new int [6][6];

    //Diagonal
    distanceTable[0][0] = 0;
    distanceTable[1][1] = 0;
    distanceTable[2][2] = 0;
    distanceTable[3][3] = 0;
    distanceTable[4][4] = 0;
    distanceTable[5][5] = 0;

    //Row 1
    distanceTable[0][1] = 1;
    distanceTable[0][2] = 2;
    distanceTable[0][3] = 3;
    distanceTable[0][4] = 2;
    distanceTable[0][5] = 1;

    distanceTable[1][0] = 1;
    distanceTable[2][0] = 2;
    distanceTable[3][0] = 3;
    distanceTable[4][0] = 2;
    distanceTable[5][0] = 1;

    //Row 2
    distanceTable[1][2] = 1;
    distanceTable[1][3] = 2;
    distanceTable[1][4] = 3;
    distanceTable[1][5] = 2;

    distanceTable[2][1] = 1;
    distanceTable[3][1] = 2;
    distanceTable[4][1] = 3;
    distanceTable[5][1] = 2;

    //Row 3
    distanceTable[2][3] = 1;
    distanceTable[2][4] = 2;
    distanceTable[2][5] = 3;

    distanceTable[3][2] = 1;
    distanceTable[4][2] = 2;
    distanceTable[5][2] = 3;

    //Row 4
    distanceTable[4][3] = 1;
    distanceTable[5][3] = 2;

    distanceTable[3][4] = 1;
    distanceTable[3][5] = 2;

    //Row 5
    distanceTable[4][5] = 1;

    distanceTable[5][4] = 1;

    /*
     * Case 1
     */
    // [0, 1, 2, 3, 4, 5, 0]
    int [] resultArray = Optimize.buildPlacesArray(7);
    resultArray[6] = 0;

    // [0, 4, 3, 2, 1, 5, 0]
    int [] placesArray = Optimize.buildPlacesArray(7);
    placesArray[6] = 0;
    Optimize.reversePlaces(placesArray, 1, 4);

    assertEquals(6, Optimize.threeOpt(placesArray, distanceTable));
    assertArrayEquals(resultArray, placesArray);

    System.out.println();

    /*
     * Case 2
     */
    // [0, 2, 1, 3, 4, 5, 0]
    placesArray = Optimize.buildPlacesArray(7);
    placesArray[6] = 0;
    Optimize.reversePlaces(placesArray, 1, 2);

    assertEquals(6, Optimize.threeOpt(placesArray, distanceTable));
    assertArrayEquals(resultArray, placesArray);

    System.out.println();

    /*
     * Case 3
     */
    // [0, 1, 2, 3, 5, 4, 0]
    placesArray = Optimize.buildPlacesArray(7);
    placesArray[6] = 0;
    Optimize.reversePlaces(placesArray, 4, 5);

    assertEquals(6, Optimize.threeOpt(placesArray, distanceTable));
    assertArrayEquals(resultArray, placesArray);

    System.out.println();

    /*
     * Case 4
     */
    // [0, 4, 3, 1, 2, 5, 0]
    placesArray = Optimize.buildPlacesArray(7);
    placesArray[6] = 0;
    Optimize.reversePlaces(placesArray, 3, 4);
    Optimize.swapBlocks(placesArray, 1, 2, 3, 4);

    assertEquals(6, Optimize.threeOpt(placesArray, distanceTable));
    assertArrayEquals(resultArray, placesArray);

    System.out.println();

    /*
     * Case 5
     */
    // [0, 3, 4, 2, 1, 5, 0]
    placesArray = Optimize.buildPlacesArray(7);
    placesArray[6] = 0;
    Optimize.reversePlaces(placesArray, 1, 2);
    Optimize.swapBlocks(placesArray, 1, 2, 3, 4);

    assertEquals(6, Optimize.threeOpt(placesArray, distanceTable));
    assertArrayEquals(resultArray, placesArray);

    System.out.println();

    /*
     * Case 6
     */
    // [0, 2, 1, 4, 3, 5, 0]
    placesArray = Optimize.buildPlacesArray(7);
    placesArray[6] = 0;
    Optimize.reversePlaces(placesArray, 1, 2);
    Optimize.reversePlaces(placesArray, 3, 4);
    Optimize.swapBlocks(placesArray, 1, 2, 3, 4);

    assertEquals(6, Optimize.threeOpt(placesArray, distanceTable));
    assertArrayEquals(resultArray, placesArray);

    System.out.println();

    /*
     * Case 7
     */
    // [0, 3, 4, 1, 2, 5, 0]
    placesArray = Optimize.buildPlacesArray(7);
    placesArray[6] = 0;
    Optimize.swapBlocks(placesArray, 1, 2, 3, 4);

    assertEquals(6, Optimize.threeOpt(placesArray, distanceTable));
    assertArrayEquals(resultArray, placesArray);

    System.out.println();



  }

  /**
   * Swap block test block of swapping.
   */

  @Test
  public void testSwapBlocks1(){
    //[0,1,2,3,4]
    int [] testArray = Optimize.buildPlacesArray(5);

    //[2,3,0,1,4]
    int [] resultArray = new int [5];
    resultArray[0] = 2;
    resultArray[1] = 3;
    resultArray[2] = 0;
    resultArray[3] = 1;
    resultArray[4] = 4;

    Optimize.swapBlocks(testArray, 0, 1, 2, 3);
    assertArrayEquals(resultArray, testArray);
//----------
    //[0,1,2,3,4]
    testArray = Optimize.buildPlacesArray(5);
    //[1,2,0,3,4]
    resultArray[0] = 1;
    resultArray[1] = 2;
    resultArray[2] = 0;
    resultArray[3] = 3;
    resultArray[4] = 4;

    Optimize.swapBlocks(testArray, 0, 0, 1, 2);
    assertArrayEquals(resultArray, testArray);
//----------
    //[0,1,2,3,4]
    testArray = Optimize.buildPlacesArray(5);
    //[2,0,1,3,4]
    resultArray[0] = 2;
    resultArray[1] = 0;
    resultArray[2] = 1;
    resultArray[3] = 3;
    resultArray[4] = 4;

    Optimize.swapBlocks(testArray, 0, 1, 2, 2);
    assertArrayEquals(resultArray, testArray);
//----------
    //[0,1,2,3,4]
    testArray = Optimize.buildPlacesArray(5);
    //[3,4,2,0,1]
    resultArray[0] = 3;
    resultArray[1] = 4;
    resultArray[2] = 2;
    resultArray[3] = 0;
    resultArray[4] = 1;

    Optimize.swapBlocks(testArray, 0, 1, 3, 4);
    assertArrayEquals(resultArray, testArray);















  }









}




























