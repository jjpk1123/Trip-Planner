package com.tripco.t17.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t17.server.HTTP;
import spark.Request;
import java.util.ArrayList;
import java.lang.Math;

/**
 * The Trip class supports TFFI so it can easily be converted to/from Json by Gson.
 *
 */
public class Trip {
  // The variables in this class should reflect TFFI.
  public String type;
  public String title;
  public Option options;
  public ArrayList<Place> places;
  public ArrayList<Integer> distances;
  public String map;

  /** The top level method that does planning.
   * At this point it just adds the map and distances for the places in order.
   * It might need to reorder the places in the future.
   */
  public void plan() {
    this.map = svg();
    this.distances = legDistances();

  }

  /**
   * Returns an SVG containing the background and the legs of the trip.
   * @return
   */
  private String svg() {
    // TODO: return the user-specific svg. Maybe create a helper method to assist.
    // hardcoded example
    return "<svg width=\"1920\" height=\"960\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><!-- Created with SVG-edit - http://svg-edit.googlecode.com/ --> <g> <g id=\"svg_4\"> <svg id=\"svg_1\" height=\"960\" width=\"1920\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\"> <g id=\"svg_2\"> <title>Layer 1</title> <rect fill=\"rgb(119, 204, 119)\" stroke=\"black\" x=\"0\" y=\"0\" width=\"1920\" height=\"960\" id=\"svg_3\"/> </g> </svg> </g> <g id=\"svg_9\"> <svg id=\"svg_5\" height=\"480\" width=\"960\" y=\"240\" x=\"480\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\"> <g id=\"svg_6\"> <title>Layer 2</title> <polygon points=\"0,0 960,0 960,480 0,480\" stroke-width=\"12\" stroke=\"brown\" fill=\"none\" id=\"svg_8\"/> <polyline points=\"0,0 960,480 480,0 0,480 960,0 480,480 0,0\" fill=\"none\" stroke-width=\"4\" stroke=\"blue\" id=\"svg_7\"/> </g> </svg> </g> </g> </svg>";
  }

  /**
   * Returns the distances between consecutive places,
   * including the return to the starting point to make a round trip.
   * @return
   */
  private ArrayList<Integer> legDistances() {
    ArrayList<Integer> dist = new ArrayList<Integer>();
    //System.out.println("Hello from Trip.java!");
    //String unit = this.options.distance; //Pass this to GCD
    // for(blah=0; blah < blah-1; ++blah)
    // { dist.add(helperDistanceMethod(place[a], place[a+1])) }
    // hardcoded example
      //Call trip.GCD() for each i and i+1 places from 0 to n, and then n and 0
    //We are going to do something like this
    /*for (int i = 0 ; i < this.places.size() ; i++) {
      dist.add(this.GCD(
              this.places.get(i).latitude.toDegrees(),
              this.places.get(i).longitude.toDegrees(),
              this.places.get((i + 1) % this.places.size()).latitude.toDegrees(),
              this.places.get((i + 1) % this.places.size()).longitude.toDegrees()));
    }*/
    dist.add(12);
    dist.add(23);
    dist.add(34);
    dist.add(45);
    dist.add(65);
    dist.add(19);

    return dist;
  }

  /**
   * @param DMS:    Something in one of the following forms:
   *                12.182
   *                49° 14' 46.6512" N
   *                174° 46' E
   *                69° W
   *        **Note: This takes lat OR long, not both
   * @return
   */
  //TODO: Deal with invalid input
  public double DmsToDegrees(String DMS){
    double degrees = 0.0;
    //5° 30' N
    //Check for °, main validator
    if (DMS.contains("°")){
      String[] result = DMS.split("°");
      //[5, 30' N]
      degrees = Double.parseDouble(result[0].trim());
      //Check for '
      if (result[1].contains("'")){
        result = result[1].split("'");
        double minutes = Double.parseDouble(result[0].trim());
        degrees += (minutes/60);

        //Check for "
        if (result[1].contains("\"")) {
          result = result[1].split("\"");
          double seconds = Double.parseDouble(result[0].trim());
          degrees += (seconds / 3600);
        }
      }

      //Set the sign at the very end
      if (result[1].trim().equals("N")){
      }
      else if (result[1].trim().equals("E")){
      }
      else if (result[1].trim().equals("S")){
        degrees *= -1;
      }
      else if (result[1].trim().equals("W")){
        degrees *= -1;
      } else {
        //TODO: Incorrect input, what do we do!?

      }
    } else { //Already in degrees, or another invalid input like "klajsdf"
      try{
        degrees = Double.parseDouble(DMS);
      } catch (Exception e){
        //TODO: Set degrees to error code, maybe 9999999 or something?
        //Perhaps we can make a new method for error handling which stops legDistances?
      }
    }

    return degrees;
  }

  /**
   * @param source: starting point, contains lat/long
   * @param dest:   ending point, contains lat/long
   * @param unit:   calculation of radius, km or miles
   * @return
   */
  public int GCD(Place source, Place dest, String unit){
    //Source (a1,b1)
    double a1 = Math.toRadians(this.DmsToDegrees(source.latitude));
    double b1 = Math.toRadians(this.DmsToDegrees(source.longitude));

    //Dest (a2,b2)
    double a2 = Math.toRadians(this.DmsToDegrees(dest.latitude));
    double b2 = Math.toRadians(this.DmsToDegrees(dest.longitude));

    //Compute X,Y,Z
    double x = Math.cos(a2)*Math.cos(b2) - Math.cos(a1)*Math.cos(b1);
    double y = Math.cos(a2)*Math.sin(b2) - Math.cos(a1)*Math.sin(b1);
    double z = Math.sin(a2) - Math.sin(a1);

    //Compute chord length
    double c = Math.sqrt((x*x)+(y*y)+(z*z));

    //Compute central angle
    double o = 2*(Math.asin(c/2));

    //Find greatest circle distance depending on unit
    double d;
    if (unit.equals("miles")){
      d = o * 3958.7613;
    }else{ //Kilometers
      d = o * 6371.0088;
    }

    //Do some Math magic
    int result = Math.toIntExact(Math.round(d));

    return result;
  }

}