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
     */
    public void plan() {
        if (this.options.optimization == "1"){
            this.places = Optimize.nearestNeighbor(this.places);
        }
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
        try{
            double[] coord1 = svgHelper(this.DmsToDegrees(this.places.get(from).latitude), this.DmsToDegrees(this.places.get(from).longitude));
            double[] coord2 = svgHelper(this.DmsToDegrees(this.places.get(to).latitude), this.DmsToDegrees(this.places.get(to).longitude));

            ln = "\n<line x1=\"" + Double.toString(coord1[0]) + "\" y1=\"" + Double.toString(coord1[1]) + "\"";
            ln +=       " x2=\"" + Double.toString(coord2[0]) + "\" y2=\"" + Double.toString(coord2[1]) + "\"";
            ln += " style=\"stroke:rgb(255,0,0);stroke-width:3\" />";

        } catch (Exception e){

        }
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
        double latToSVG = (747.0 - 37.0)/(41.0 - 37.0);
        double longToSVG = (1030.0 - 36.0)/(109.0 - 102.0);
        double x, y;
        //So, some scaling and translation and such.
        x = (109.0 - Math.abs(lon)) * longToSVG + 37;
        y = (41.0 - Math.abs(lat)) * latToSVG + 36;

        return new double[]{x, y};
    }

    /**
     * Returns the distances between consecutive places,
     * including the return to the starting point to make a round trip.
     *
     * @return
     */
    private ArrayList<Integer> legDistances() {
        ArrayList<Integer> dist = new ArrayList<>();
        //If 0 Places
        if (this.places.size() < 1){
            return dist;
        }

        //If 1 or more places
        for (int i = 0 ; i < this.places.size() ; i++) {
            dist.add(this.GCD(this.places.get(i),
                    this.places.get((i+1)%this.places.size()),
                    this.options.distance));
        }
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
    public static double DmsToDegrees(String DMS) {
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

            }
        } else { //Already in degrees, or another invalid input like "klajsdf"
            try {
                degrees = Double.parseDouble(DMS);
            } catch (Exception e) {
                //Perhaps we can make a new method for error handling which stops legDistances?
                System.err.println(e);
                throw e;
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
    public static int GCD(Place source, Place dest, String unit) {
        //Source (a1,b1)
        double a1, a2, b1, b2 = 0;
        try {
            a1 = Math.toRadians(DmsToDegrees(source.latitude));
            b1 = Math.toRadians(DmsToDegrees(source.longitude));
        } catch (Exception e){
            System.err.println(e);
            return -1; //bad source
        }

        //Dest (a2,b2)
        try{
            a2 = Math.toRadians(DmsToDegrees(dest.latitude));
            b2 = Math.toRadians(DmsToDegrees(dest.longitude));
        } catch (Exception e){
            System.err.println(e);
            return -2; //Bad dest
        }
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
