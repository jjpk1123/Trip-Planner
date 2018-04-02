package com.tripco.t17.planner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Svg {

    private ArrayList<Place> places;
    String map;

    /**
     * Create Svg object.
     * @param places populates places for the svg() method
     */
    public Svg(ArrayList<Place> places){
        this.places = places;
        try {
            this.map = svg();
        } catch(IOException e){
            System.err.println(e);
        }
    }

    /**
     * Returns an SVG containing the background and the legs of the trip.
     *
     * @return map with lines on it.
     */
    private String svg() throws IOException {
        //SVG formatting. Not the prettiest, but it works.
//        String map = "<svg width=\"1066.6073\" height=\"783.0824\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\">";
//        map += "<svg width=\"1066.6073\" height=\"783.0824\">";

        String map = "<svg width=\"1024\" height=\"512\">";
        InputStream is = getClass().getResourceAsStream("/World_Map.svg");
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
        if (places == null || places.size() == 0) {
            //System.out.println("No places==No SVG");
            return map + "</svg>";
        }
        //Write a line for each place
        for (int i = 0; i < places.size()-1; i++) {
            map += svgLine(i, i + 1);
        }
        //Also include return trip to first element. (If we change how this works, look here!)
        map += svgLine(places.size() - 1, 0);

        //Included for testing purposes.
        //System.out.println(map); // (This should be the map svg, including the round trip path).
        return map + "</svg>";
    }

    /**
     * Returns a String for a single line in SVG format.
     * @param from starting location
     * @param to ending location
     * @return line between one place to another.
     */
    private String svgLine(int from, int to){
        String line = "";
        try{
            double[] coord1 = svgHelper(Distance.dmsToDegrees(places.get(from).latitude),
                                        Distance.dmsToDegrees(places.get(from).longitude));
            double[] coord2 = svgHelper(Distance.dmsToDegrees(places.get(to).latitude),
                                        Distance.dmsToDegrees(places.get(to).longitude));

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
     * @param lat latitude
     * @param lon longitude
     * @return magic
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

//        double latToSvg = (747.0 - 37.0)/(41.0 - 37.0);
//        double longToSvg = (1030.0 - 36.0)/(109.0 - 102.0);
//
//        //So, some scaling and translation and such.
//        double x1 = (109.0 - Math.abs(lon)) * longToSvg + 37;
//        double y1 = (41.0 - Math.abs(lat)) * latToSvg + 36;

        // World West  X value on SVG = 0
        // World East  X value on SVG = 1024
        // World North Y value on SVG = 0
        // World South Y value on SVG = 512

        // West  degrees = -180
        // East  degrees =  180
        // North degrees =  90
        // South degrees = -90
        double x1 = 0;
        double y1 = 0;

        return new double[]{x1, y1};
    }


}
