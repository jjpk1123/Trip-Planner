package com.tripco.t17.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t17.server.HTTP;
import spark.Request;

import java.util.ArrayList;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Trip class supports TFFI so it can easily be converted to/from Json by Gson.
 */
public class Trip {
    // The variables in this class should reflect TFFI.
    public String type;
    public String title;
    public Option options;
    public ArrayList<Place> places;
    public ArrayList<Integer> distances;
    public String map;

    /**
     * The top level method that does planning.
     * At this point it just adds the map and distances for the places in order.
     * It might need to reorder the places in the future.
     */
    public void plan() {
        try {
            this.map = svg();
        }catch(IOException e){
            System.err.println(e);
        }
        this.distances = legDistances();

    }

    /**
     * Returns an SVG containing the background and the legs of the trip.
     *
     * @return
     */
    private String svg() throws IOException {
        //SVG formatting. Not the prettiest, but it works.
        String map = "<svg width=\"1066.6073\" height=\"783.0824\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\">";
        map += "<svg width=\"1066.6073\" height=\"783.0824\">";

        //As written in the piazza post, seems to work out.
        InputStream is = getClass().getResourceAsStream("/CObackgroundMap.svg");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //If the map isn't found, we've got trouble down here in River City.
        try {
            while (br.ready())
                map += br.readLine() + "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
        br.close();
        //Error checking, just in case this gets called before places has anything. Would it be better to print a message about no input data?
        if (this.places == null || this.places.size() == 0) {
            return map + "</svg></svg>";
        }
        //Write a line for each place
        for (int i = 0; i < this.places.size() - 1; i++) {
            map += svgLine(i, i + 1);
        }
        //Also include return trip to first element. (If we change how this works, look here!)
        map += svgLine(this.places.size() - 1, 0);

        map += "</svg>\n</svg>";
        //Included for testing purposes.
        //System.out.println(map); // (This should be the map svg, including the round trip path).
        return map;
    }

    /**
     * Returns a String for a single line in SVG format
     * @param from
     * @param to
     * @return
     */
    private String svgLine(int from, int to){
        String ln = "";

        double[] coord1 = svgHelper(Double.parseDouble(this.places.get(from).latitude), Double.parseDouble(this.places.get(from).longitude));
        double[] coord2 = svgHelper(Double.parseDouble(this.places.get(to).latitude), Double.parseDouble(this.places.get(to).longitude));

        ln = "\n<line x1=\"" + Double.toString(coord1[0]) + "\" y1=\"" + Double.toString(coord1[1]) + "\"";
        ln +=       " x2=\"" + Double.toString(coord2[0]) + "\" y2=\"" + Double.toString(coord2[1]) + "\"";
        ln += " style=\"stroke:rgb(255,0,0);stroke-width:3\" />";

        return ln;
    }

    /**
     * Returns SVG coordinates of a point
     * @param lat
     * @param lon
     * @return
     */
    private double[] svgHelper(double lat, double lon){
        // Colorado West X value on SVG = 1030
        // Colorado East X value on SVG = 36
        // Colorado North Y value on SVG = 37
        // Colorado South Y value on SVG = 747
        // Colorado West border = 41 W
        // Colorado East border = 37 W
        // Colorado North border = 102 N
        // Colorado South border = 109 S
        double latToSVG = (1030.0 - 36.0)/(41.0 - 37.0);
        double longToSVG = (747.0 - 37.0)/(109.0 - 102.0);
        double x, y;
        //So, some scaling and translation and such.
        y = (109.0 - Math.abs(lon)) * longToSVG + 37;
        x = (41.0 - Math.abs(lat)) * latToSVG + 36;

        return new double[]{x, y};
    }

    /**
     * Returns the distances between consecutive places,
     * including the return to the starting point to make a round trip.
     *
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
     * @param DMS: Something in one of the following forms:
     *             12.182
     *             49° 14' 46.6512" N
     *             174° 46' E
     *             69° W
     *             **Note: This takes lat OR long, not both
     * @return
     */
    //TODO: Deal with invalid input
    public double DmsToDegrees(String DMS) {
        double degrees = 0.0;
        //5° 30' N
        //Check for °, main validator
        if (DMS.contains("°")) {
            String[] result = DMS.split("°");
            //[5, 30' N]
            degrees = Double.parseDouble(result[0].trim());
            //Check for '
            if (result[1].contains("'")) {
                result = result[1].split("'");
                double minutes = Double.parseDouble(result[0].trim());
                degrees += (minutes / 60);

                //Check for "
                if (result[1].contains("\"")) {
                    result = result[1].split("\"");
                    double seconds = Double.parseDouble(result[0].trim());
                    degrees += (seconds / 3600);
                }
            }

            //Set the sign at the very end
            if (result[1].trim().equals("N")) {
            } else if (result[1].trim().equals("E")) {
            } else if (result[1].trim().equals("S")) {
                degrees *= -1;
            } else if (result[1].trim().equals("W")) {
                degrees *= -1;
            } else {
                //TODO: Incorrect input, what do we do!?

            }
        } else { //Already in degrees, or another invalid input like "klajsdf"
            try {
                degrees = Double.parseDouble(DMS);
            } catch (Exception e) {
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
    public int GCD(Place source, Place dest, String unit) {
        //Source (a1,b1)
        double a1 = Math.toRadians(this.DmsToDegrees(source.latitude));
        double b1 = Math.toRadians(this.DmsToDegrees(source.longitude));

        //Dest (a2,b2)
        double a2 = Math.toRadians(this.DmsToDegrees(dest.latitude));
        double b2 = Math.toRadians(this.DmsToDegrees(dest.longitude));

        //Compute X,Y,Z
        double x = Math.cos(a2) * Math.cos(b2) - Math.cos(a1) * Math.cos(b1);
        double y = Math.cos(a2) * Math.sin(b2) - Math.cos(a1) * Math.sin(b1);
        double z = Math.sin(a2) - Math.sin(a1);

        //Compute chord length
        double c = Math.sqrt((x * x) + (y * y) + (z * z));

        //Compute central angle
        double o = 2 * (Math.asin(c / 2));

        //Find greatest circle distance depending on unit
        double d;
        if (unit.equals("miles")) {
            d = o * 3958.7613;
        } else { //Kilometers
            d = o * 6371.0088;
        }

        //Do some Math magic
        int result = Math.toIntExact(Math.round(d));

        return result;
    }

}