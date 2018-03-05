package com.tripco.t17.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t17.server.HTTP;

import java.util.ArrayList;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import spark.Request;

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
        //1. Plan the trip
        if (this.options.optimization.equals("1")){
            this.places = Optimize.nearestNeighbor(this.places);
        } //else 0 = no optimization

        //2. Draw the map of the plan
        try {
            this.map = svg();
        }catch(IOException e){
            System.err.println(e);
        }

        //3. Find distances
        this.distances = Distance.legDistances(this.places, this.options.distance);

    }

    /**
     * Returns an SVG containing the background and the legs of the trip.
     *
     * @return map with lines on it.
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
            while (br.ready()) {
                map += br.readLine() + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        br.close();
        //Error checking, just in case this gets called before places has anything.
        //Would it be better to print a message about no input data?
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
     * Returns a String for a single line in SVG format.
     * @param from
     * @param to
     * @return line between one place to another.
     */
    private String svgLine(int from, int to){
        String line = "";
        try{
            double[] coord1 = svgHelper(Distance.dmsToDegrees(this.places.get(from).latitude),
                                        Distance.dmsToDegrees(this.places.get(from).longitude));
            double[] coord2 = svgHelper(Distance.dmsToDegrees(this.places.get(to).latitude),
                                        Distance.dmsToDegrees(this.places.get(to).longitude));

            line = "\n<line x1=\"" + Double.toString(coord1[0]) + "\" y1=\""
                                   + Double.toString(coord1[1]) + "\" x2=\""
                                   + Double.toString(coord2[0]) + "\" y2=\""
                                   + Double.toString(coord2[1]) + "\""
                 + " style=\"stroke:rgb(255,0,0);stroke-width:3\" />";
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }
        return line;
    }

    /**
     * Returns SVG coordinates of a point.
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


}
