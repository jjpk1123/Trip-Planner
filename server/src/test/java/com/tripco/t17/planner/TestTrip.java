package com.tripco.t17.planner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
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

  @Test
  public void testOptimizeOfTrip() {
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

      // No opt
      ArrayList<Integer> expectedDistances = new ArrayList<>();
      Collections.addAll(expectedDistances, 195, 158, 157, 61);
      trip.plan();
      assertEquals(expectedDistances, trip.distances);

      // With NN
      trip.options.optimization = "0.5";
      ArrayList<Integer> expectedOptimizedDistances = new ArrayList<>();
      Collections.addAll(expectedOptimizedDistances, 149, 158, 148, 61);
      trip.plan();
//      assertEquals(expectedOptimizedDistances, trip.distances);
  }

  @Test
  public void testSvgDmsToDegreesFailure() {
    Place Failure = new Place();
    Failure.latitude    = "banana";
    Failure.longitude   = "107° 40' 2  W";

    Place Correct = new Place();
    Correct.latitude    = "37°  37' 49 N";
    Correct.longitude   = "107° 48' 52 W";

    trip.places.add(Failure);
    trip.places.add(Correct);

    try {
      trip.plan();
    }
    catch (NumberFormatException actual) {
      NumberFormatException expected = new NumberFormatException("For input string: \"banana\"");
      assertEquals(expected.toString().substring(0, expected.toString().length()), actual.toString());
    }
  }

/*  @Test
  public void testSvgReadMapDirectoryFailure() {
    Place Correct1 = new Place();
    Correct1.latitude    = "37°  56' 11 N";
    Correct1.longitude   = "107° 40' 2  W";

    Place Correct2 = new Place();
    Correct2.latitude    = "37°  37' 49 N";
    Correct2.longitude   = "107° 48' 52 W";

    trip.places.add(Correct1);
    trip.places.add(Correct2);

    try {
      Svg svg = new Svg(trip.places, "/World_map.svg");
    } catch (Exception actual) {
      IOException expected = new IOException("this needs to change");
      assertEquals(expected, actual);
    }
  }*/


}
