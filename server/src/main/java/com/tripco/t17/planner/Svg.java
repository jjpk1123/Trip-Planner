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
    public Svg(ArrayList<Place> places, String mapDirectory){
        this.places = places;
        try {
            this.map = svg(mapDirectory);
        }
        catch(Exception e) {
            // System.err.println(e);
        }
    }

    /**
     * Returns an SVG containing the background and the legs of the trip.
     *
     * @return map with lines on it.
     */
    private String svg(String mapDirectory) throws IOException {
        String map = retrieveSvgIntro();

        InputStream is = getClass().getResourceAsStream(mapDirectory);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        while (br.ready()) {
            map += br.readLine() + "\n";
        }
        br.close();

        //Error checking, just in case this gets called before places has anything.
        if (places == null || places.size() == 0) {
            // System.out.println("No places==No SVG");
            map += "\n</svg>\n</svg>";
            return map;
        }

        map += retrieveLines();

        map += "\n</svg>\n</svg>";
//        System.out.println(map); // debug
        return map;
    }

    /**
     * Only to make the svg() method shorter
     * @return the introductory String the svg object needs
     */
    private String retrieveSvgIntro() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
                + "<svg width=\"1024\" height=\"512\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" "
                + "xmlns:cc=\"http://web.resource.org/cc/\" "
                + "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" "
                + "xmlns:svg=\"http://www.w3.org/2000/svg\" "
                + "xmlns=\"http://www.w3.org/2000/svg\" "
                + "xmlns:sodipodi=\"http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd\" "
                + "xmlns:inkscape=\"http://www.inkscape.org/namespaces/inkscape\">\n"
                + "<svg width=\"1024\" height=\"512\">";
    }

    /**
     * Creates (red) lines that connect each place (place A -> place B)
     * return lines = all the lines needed
     */
    private String retrieveLines() {
        String sendIt = "";
        for (int i = 0; i < places.size()-1; i++) {
            sendIt += svgLine(i, i + 1);
        }
        //Creates the line that goes from the last place back to first place
        sendIt += svgLine(places.size()-1, 0);
        return sendIt;
    }

    /**
     * Returns a String for a single line in SVG format.
     * @param from starting location
     * @param to ending location
     * @return line between one place to another.
     */
    private String svgLine(int from, int to) {
        try {
            double latA = Distance.dmsToDegrees(places.get(from).latitude);
            double lonA = Distance.dmsToDegrees(places.get(from).longitude);
            double latB = Distance.dmsToDegrees(places.get(to).latitude);
            double lonB = Distance.dmsToDegrees(places.get(to).longitude);

            int whichDirection = crossingTheEdge(lonA, lonB); // checks if it crosses the edge

            if (whichDirection != 0) {
                return clipTheEdge(whichDirection, latA, lonA, latB, lonB);
            }
            else {
                double[] coordA = svgHelper(latA, lonA);
                double[] coordB = svgHelper(latB, lonB);
                return drawTheLine(coordA[0], coordA[1], coordB[0], coordB[1]);
            }
        } catch(Exception e){
            //System.err.println(e);
            throw e;
        }
    }

    /**
     * Returns SVG coordinates of the given point.
     * @param lat = latitude
     * @param lon = longitude
     * @return [0]=x; [1]=y;
     */
    private double[] svgHelper(double lat, double lon){
        double deltaX = (lon / 180) * 512; // the y value, relative to svg pixels
        double deltaY = (lat /  90) * 256; // the x value, relative to svg pixels
        double svgX = 512 + deltaX;
        double svgY = 256 - deltaY;

        return new double[]{svgX, svgY};
    }

    /**
     * Simply draws the line from pointA->pointB.
     * Can be called with regular points and with points that cross the boundaries.
     * @param latA the latitude of pointA
     * @param lonA the longitude of pointA
     * @param latB the latitude of pointB
     * @param lonB the longitude of pointB
     * @return line = the SVG line drawn from pointA(latA, lonA)->pointB(latB, lonB)
     */
    private String drawTheLine(double latA, double lonA, double latB, double lonB) {
        return "\n<line x1=\"" + Double.toString(latA)
                + "\" y1=\"" + Double.toString(lonA)
                + "\" x2=\"" + Double.toString(latB)
                + "\" y2=\"" + Double.toString(lonB)
                + "\" style=\"stroke:rgb(255,0,0);stroke-width:1\" />";
    }

    /*From here on deals with Svg points that cross the left/right borders of the map*/

    /**
     * Does it cross the edge of the SVG map?
     *  0=="no"
     *  1=="Yes; right->left"
     * -1=="Yes; left->right"
     * @param lonA the longitude of pointA
     * @param lonB the longitude of pointB
     * @return the number described 3-5 lines above
     */
    private int crossingTheEdge(double lonA, double lonB) {
        if (Math.abs(lonA - lonB) > 180.0) { // if they are > half a world away
            if (lonA > 0 && lonB < 0) {
                return 1; // if we need to draw a line left->right (right border)
            }
            return -1;  // else we need to draw a line right->left (left border)
        }
        return 0; // else (normal) they are < half a world away
    }

    /**
     * Draws both sides of the line; "Clipping" as Dave calls it
     *  1=="Yes; right->left"
     * -1=="Yes; left->right"
     * @param whichDirection -1|1 as described 1-2 lines above
     * @param latA the latitude of pointA
     * @param lonA the longitude of pointA
     * @param latB the latitude of pointB
     * @param lonB the longitude of pointB
     * @return both lines that will be appended to the end of the SVG
     */
    private String clipTheEdge(int whichDirection, double latA, double lonA, double latB, double lonB) {
        String line;
        double difference = calculateDifference(whichDirection, lonA, lonB); // right->left

        line =  drawFirstLine(difference, latA, lonA, latB);
        line += drawSecondLine(difference, latA, latB, lonB);

        return line;
    }

    /**
     * Calculates the difference between pointA and pointB's longitude.
     *  1=="Yes; right->left"
     * -1=="Yes; left->right"
     * Returns the difference. If (left->right) -diff; Else +diff
     * @param whichDirection -1|1 as described 2-3 lines above
     * @param lonA the longitude of pointA
     * @param lonB the longitude of pointB
     * @return the difference.
     */
    private double calculateDifference(double whichDirection, double lonA, double lonB) {
        double diff;
        if (whichDirection > 0) {
            diff = 180 - lonA;
            diff += 180 + lonB;
        }
        else {
            diff = -180 - lonA;
            diff -= 180 - lonB;
        }
        return diff;
    }

    /**
     * Draws the first line from pointA->(pointA+difference).
     * @return the Svg line
     */
    private String drawFirstLine(double difference, double latA, double lonA, double latB) {
        double[] coordA = svgHelper(latA, lonA);
        double[] coordB = svgHelper(latB, lonA + difference);

        return drawTheLine(coordA[0], coordA[1], coordB[0], coordB[1]);
    }

    /**
     * Draws the second line from (pointB-difference) -> pointB
     * @return the Svg line
     */
    private String drawSecondLine(double difference, double latA, double latB, double lonB) {
        double[] coordA = svgHelper(latA, lonB - difference);
        double[] coordB = svgHelper(latB, lonB);

        return drawTheLine(coordA[0], coordA[1], coordB[0], coordB[1]);
    }
}

