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
            // System.err.println(e);
        }
    }

    /**
     * Returns an SVG containing the background and the legs of the trip.
     *
     * @return map with lines on it.
     */
    private String svg() throws IOException {
        //SVG formatting. Not the prettiest, but it works.
        String map = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
        map += "<svg width=\"1024\" height=\"512\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" " +
                "xmlns:cc=\"http://web.resource.org/cc/\" " +
                "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" " +
                "xmlns:svg=\"http://www.w3.org/2000/svg\" " +
                "xmlns=\"http://www.w3.org/2000/svg\" " +
                "xmlns:sodipodi=\"http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd\" " +
                "xmlns:inkscape=\"http://www.inkscape.org/namespaces/inkscape\">\n";
        map += "<svg width=\"1024\" height=\"512\">";

        InputStream is = getClass().getResourceAsStream("/World_Map.svg");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

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
            // System.out.println("No places==No SVG");
            map += "\n</svg>\n</svg>";
            return map;
        }
        //Creates a line for each (place A -> place B)
        for (int i = 0; i < places.size()-1; i++) {
            map += svgLine(i, i + 1);
        }
        //Creates the line that goes from the last place back to first place
        map += svgLine(places.size()-1, 0);

        map += "\n</svg>\n</svg>";
//        System.out.println(map); // debug
        return map;
    }

    /**
     * Returns a String for a single line in SVG format.
     * @param from starting location
     * @param to ending location
     * @return line between one place to another.
     */
    private String svgLine(int from, int to){
        String line;
        try{
            double[] coord1 = svgHelper(Distance.dmsToDegrees(places.get(from).latitude),
                                        Distance.dmsToDegrees(places.get(from).longitude));
            double[] coord2 = svgHelper(Distance.dmsToDegrees(places.get(to).latitude),
                                        Distance.dmsToDegrees(places.get(to).longitude));

            line = "\n<line x1=\"" + Double.toString(coord1[0]) + "\" y1=\""
                                   + Double.toString(coord1[1]) + "\" x2=\""
                                   + Double.toString(coord2[0]) + "\" y2=\""
                                   + Double.toString(coord2[1]) + "\""
                 + " style=\"stroke:rgb(255,0,0);stroke-width:1\" />";
        } catch (Exception e) {
            //System.err.println(e);
            throw e;
        }
        return line;
    }

    /**
     * Returns SVG coordinates of the given point.
     * @param lat = latitude
     * @param lon = longitude
     * @return [0]=x; [1]=y;
     */
    private double[] svgHelper(double lat, double lon){
        double delta_x = (lat /  90) * 256; // the x value, relative to svg pixels
        double delta_y = (lon / 180) * 512; // the y value, relative to svg pixels
        double x;
        double y;

        y = 256 - delta_x;
        x = 512 + delta_y;
        return new double[]{x, y};
    }
}
